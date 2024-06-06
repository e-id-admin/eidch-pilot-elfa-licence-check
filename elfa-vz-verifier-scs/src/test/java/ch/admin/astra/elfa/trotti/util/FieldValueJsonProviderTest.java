package ch.admin.astra.elfa.trotti.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FieldValueJsonProviderTest {

    private FieldValueJsonProvider provider;
    private JsonGenerator generator;
    private ILoggingEvent event;

    @BeforeEach
    void setUp() {
        provider = new FieldValueJsonProvider();
        generator = mock(JsonGenerator.class);
        event = mock(ILoggingEvent.class);
    }

    @Test
    void testWriteTo() throws IOException {
        // Arrange
        String fieldName = "fieldName";
        String fieldValue = "fieldValue";
        provider.setFieldName(fieldName);
        provider.setValue(fieldValue);

        // Act
        provider.writeTo(generator, event);

        // Assert
        ArgumentCaptor<String> fieldNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> fieldValueCaptor = ArgumentCaptor.forClass(String.class);

        verify(generator, times(1)).writeStringField(fieldNameCaptor.capture(), fieldValueCaptor.capture());
        assertEquals(fieldName, fieldNameCaptor.getValue());
        assertEquals(fieldValue, fieldValueCaptor.getValue());
    }

    @Test
    void testDoesNotThrowIOException() {
        // Arrange
        String fieldName = "fieldName";
        String fieldValue = "fieldValue";
        provider.setFieldName(fieldName);
        provider.setValue(fieldValue);

        // Act & Assert
        assertDoesNotThrow( () -> provider.writeTo(generator, event));
    }
}
