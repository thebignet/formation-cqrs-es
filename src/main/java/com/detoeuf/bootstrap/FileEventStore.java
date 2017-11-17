package com.detoeuf.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.jackson.datatype.VavrModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class FileEventStore implements EventStore {
    public static final boolean APPEND = true;
    private ObjectMapper mapper;
    private final File repositoryDir;

    public FileEventStore(File repositoryDir) {
        this.repositoryDir = repositoryDir;
        mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.registerModule(new VavrModule());
    }

    private String eventAsJson(Event event) throws JsonProcessingException {
        return mapper.writer().writeValueAsString(event);
    }

    @Override
    public void appendAll(List<Event> events) {
        events.map(event -> Tuple.of(getFileNameFromAggregateId(event.getAggregateId()), event))
                .forEach(tryToSerialize());
    }

    private Consumer<Tuple2<File, Event>> tryToSerialize() {
        return handleAndEvent -> {
            AggregateId aggregateId = handleAndEvent._2.getAggregateId();
            SequenceNumber sequenceNumber = handleAndEvent._2.getSequenceNumber();
            if (lastKnownSequenceNumber(aggregateId).isNext(sequenceNumber)) {
                serialize(handleAndEvent._1, handleAndEvent._2);
            } else {
                throw new IllegalStateException();
            }
        };
    }

    private SequenceNumber lastKnownSequenceNumber(AggregateId aggregateId) {
        return getEventsOfAggregate(aggregateId)
                .lastOption()
                .map(Event::getSequenceNumber)
                .getOrElse(SequenceNumber.initial());
    }

    private File getFileNameFromAggregateId(AggregateId aggregateId) {
        return new File(repositoryDir.getAbsolutePath() + File.pathSeparator + aggregateId.serialize());
    }

    private void serialize(File filePath, Event event) {
        try (FileOutputStream os = new FileOutputStream(filePath, APPEND)) {
            os.write(eventAsJson(event).getBytes(StandardCharsets.UTF_8));
            os.write("\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> getEventsOfAggregate(AggregateId aggregateId) {
        File fileName = getFileNameFromAggregateId(aggregateId);
        try {
            return List.ofAll(Files.lines(Paths.get(fileName.toString())))
                    .map(this::deserialize);
        } catch (NoSuchFileException e) {
            return List.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Event deserialize(String json) {
        try {
            return mapper.readValue(json, Event.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
