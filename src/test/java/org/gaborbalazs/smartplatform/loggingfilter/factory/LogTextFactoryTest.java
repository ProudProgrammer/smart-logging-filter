package org.gaborbalazs.smartplatform.loggingfilter.factory;

import org.gaborbalazs.smartplatform.loggingfilter.configuration.LogConfiguration;
import org.gaborbalazs.smartplatform.loggingfilter.wrapper.BufferedRequestWrapper;
import org.gaborbalazs.smartplatform.loggingfilter.wrapper.BufferedResponseWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogTextFactoryTest {

    private static final String METHOD = "POST";
    private static final String REQUEST_URI = "test\\test";
    private static final String HEADER_NAME_1 = "header1";
    private static final String HEADER_NAME_2 = "header2";
    private static final String HEADER_NAME_3 = "header3";
    private static final Collection<String> HEADER_VALUE_1 = List.of("value1");
    private static final Collection<String> HEADER_VALUE_2 = List.of("value2");
    private static final Collection<String> HEADER_VALUE_3 = List.of("value3");
    private static final Collection<String> HEADER_VALUE_4 = List.of("value1", "value2", "value3");
    private static final Collection<String> HEADER_NAMES_WITH_THREE_ELEMENTS = List.of(HEADER_NAME_1, HEADER_NAME_2, HEADER_NAME_3);
    private static final Collection<String> HEADER_NAMES_WITH_ONE_ELEMENT = List.of(HEADER_NAME_1);
    private static final Collection<String> HEADER_NAMES_WITHOUT_ELEMENT = Collections.emptyList();
    private static final String PAYLOAD = "{\"name1\":\"value1\",\"name2\":\"value2\"}";
    private static final String QUERY_STRING = "name1=value1&name2=value2";
    private static final String REMOTE_ADDRESS = "remote.address";

    private static final String BASE_REQUEST = "Incoming request [method=POST; uri=test\\test]";
    private static final String FULL_REQUEST = "Incoming request [method=POST; uri=test\\test; query=[name1=value1&name2=value2]; client=remote.address; "
            + "headers=[header1:\"value1\", header2:\"value2\", header3:\"value3\"]; payload={\"name1\":\"value1\",\"name2\":\"value2\"}]";
    private static final String QUERY_STRING_REQUEST = "Incoming request [method=POST; uri=test\\test; query=[name1=value1&name2=value2]]";
    private static final String EMPTY_QUERY_STRING_REQUEST = "Incoming request [method=POST; uri=test\\test; query=[]]";
    private static final String CLIENT_INFO_REQUEST = "Incoming request [method=POST; uri=test\\test; client=remote.address]";
    private static final String HEADER_REQUEST_WITHOUT_ELEMENT = "Incoming request [method=POST; uri=test\\test; headers=[]]";
    private static final String HEADER_REQUEST_WITH_ONE_ELEMENT = "Incoming request [method=POST; uri=test\\test; headers=[header1:\"value1\"]]";
    private static final String HEADER_REQUEST_WITH_THREE_ELEMENTS = "Incoming request [method=POST; uri=test\\test; headers=[header1:\"value1\", header2:\"value2\", header3:\"value3\"]]";
    private static final String HEADER_REQUEST_WITH_ONE_ELEMENT_WITH_THREE_VALUES = "Incoming request [method=POST; uri=test\\test; headers=[header1:\"value1\";\"value2\";\"value3\"]]";
    private static final String PAYLOAD_REQUEST = "Incoming request [method=POST; uri=test\\test; payload={\"name1\":\"value1\",\"name2\":\"value2\"}]";

    private static final String BASE_RESPONSE = "Outgoing response [status-code=200]";
    private static final String FULL_RESPONSE = "Outgoing response [status-code=200; headers=[header1:\"value1\", header2:\"value2\", header3:\"value3\"]; "
            + "payload={\"name1\":\"value1\",\"name2\":\"value2\"}]";
    private static final String HEADER_RESPONSE_WITHOUT_ELEMENT = "Outgoing response [status-code=200; headers=[]]";
    private static final String HEADER_RESPONSE_WITH_ONE_ELEMENT = "Outgoing response [status-code=200; headers=[header1:\"value1\"]]";
    private static final String HEADER_RESPONSE_WITH_THREE_ELEMENTS = "Outgoing response [status-code=200; headers=[header1:\"value1\", header2:\"value2\", header3:\"value3\"]]";
    private static final String PAYLOAD_RESPONSE = "Outgoing response [status-code=200; payload={\"name1\":\"value1\",\"name2\":\"value2\"}]";

    @InjectMocks
    private LogTextFactory underTest;

    @Mock
    private BufferedRequestWrapper bufferedRequestWrapper;

    @Mock
    private BufferedResponseWrapper bufferedResponseWrapper;

    @Test
    void testCreateRequestLogText() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(false);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(BASE_REQUEST, result);
    }

    @Test
    void testCreateRequestLogTextWhenQueryStringAndClientInfoAndHeadersAndPayloadIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(true);
        logConfiguration.setIncludeRequestClientInfo(true);
        logConfiguration.setIncludeRequestHeaders(true);
        logConfiguration.setIncludeRequestPayload(true);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getQueryString()).thenReturn(QUERY_STRING);
        when(bufferedRequestWrapper.getRemoteAddr()).thenReturn(REMOTE_ADDRESS);
        when(bufferedRequestWrapper.getHeaderNames()).thenReturn(Collections.enumeration(HEADER_NAMES_WITH_THREE_ELEMENTS));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_1)).thenReturn(Collections.enumeration(HEADER_VALUE_1));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_2)).thenReturn(Collections.enumeration(HEADER_VALUE_2));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_3)).thenReturn(Collections.enumeration(HEADER_VALUE_3));
        when(bufferedRequestWrapper.getBody()).thenReturn(PAYLOAD);

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(FULL_REQUEST, result);
    }

    @Test
    void testCreateRequestLogTextWhenQueryStringIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(true);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(false);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getQueryString()).thenReturn(QUERY_STRING);

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(QUERY_STRING_REQUEST, result);
    }

    @Test
    void testCreateRequestLogTextWhenEmptyQueryStringIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(true);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(false);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getQueryString()).thenReturn(null);

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(EMPTY_QUERY_STRING_REQUEST, result);
    }

    @Test
    void testCreateRequestLogTextWhenClientInfoIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(true);
        logConfiguration.setIncludeRequestHeaders(false);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getRemoteAddr()).thenReturn(REMOTE_ADDRESS);

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(CLIENT_INFO_REQUEST, result);
    }

    @Test
    void testCreateRequestLogTextWhenHeadersIncludedWithoutElement() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(true);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getHeaderNames()).thenReturn(Collections.emptyEnumeration());

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_REQUEST_WITHOUT_ELEMENT, result);
    }

    @Test
    void testCreateRequestLogTextWhenHeadersIncludedWithOneElement() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(true);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getHeaderNames()).thenReturn(Collections.enumeration(HEADER_NAMES_WITH_ONE_ELEMENT));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_1)).thenReturn(Collections.enumeration(HEADER_VALUE_1));

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_REQUEST_WITH_ONE_ELEMENT, result);
    }

    @Test
    void testCreateRequestLogTextWhenHeadersIncludedWithOneElementWithThreeValues() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(true);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getHeaderNames()).thenReturn(Collections.enumeration(HEADER_NAMES_WITH_ONE_ELEMENT));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_1)).thenReturn(Collections.enumeration(HEADER_VALUE_4));

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_REQUEST_WITH_ONE_ELEMENT_WITH_THREE_VALUES, result);
    }

    @Test
    void testCreateRequestLogTextWhenHeadersIncludedWithThreeElements() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(true);
        logConfiguration.setIncludeRequestPayload(false);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getHeaderNames()).thenReturn(Collections.enumeration(HEADER_NAMES_WITH_THREE_ELEMENTS));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_1)).thenReturn(Collections.enumeration(HEADER_VALUE_1));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_2)).thenReturn(Collections.enumeration(HEADER_VALUE_2));
        when(bufferedRequestWrapper.getHeaders(HEADER_NAME_3)).thenReturn(Collections.enumeration(HEADER_VALUE_3));

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_REQUEST_WITH_THREE_ELEMENTS, result);
    }

    @Test
    void testCreateRequestLogTextWhenPayloadIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeRequestQueryString(false);
        logConfiguration.setIncludeRequestClientInfo(false);
        logConfiguration.setIncludeRequestHeaders(false);
        logConfiguration.setIncludeRequestPayload(true);
        when(bufferedRequestWrapper.getMethod()).thenReturn(METHOD);
        when(bufferedRequestWrapper.getRequestURI()).thenReturn(REQUEST_URI);
        when(bufferedRequestWrapper.getBody()).thenReturn(PAYLOAD);

        // WHEN
        String result = underTest.createRequestLogText(bufferedRequestWrapper, logConfiguration);

        // THEN
        assertEquals(PAYLOAD_REQUEST, result);
    }

    @Test
    void testCreateResponseLogText() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeResponseHeaders(false);
        logConfiguration.setIncludeResponsePayload(false);
        when(bufferedResponseWrapper.getStatus()).thenReturn(200);

        // WHEN
        String result = underTest.createResponseLogText(bufferedResponseWrapper, logConfiguration);

        // THEN
        assertEquals(BASE_RESPONSE, result);
    }

    @Test
    void testCreateResponseLogTextWhenHeadersAndPayloadIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeResponseHeaders(true);
        logConfiguration.setIncludeResponsePayload(true);
        when(bufferedResponseWrapper.getStatus()).thenReturn(200);
        when(bufferedResponseWrapper.getHeaderNames()).thenReturn(HEADER_NAMES_WITH_THREE_ELEMENTS);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_1)).thenReturn(HEADER_VALUE_1);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_2)).thenReturn(HEADER_VALUE_2);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_3)).thenReturn(HEADER_VALUE_3);
        when(bufferedResponseWrapper.getBody()).thenReturn(PAYLOAD);

        // WHEN
        String result = underTest.createResponseLogText(bufferedResponseWrapper, logConfiguration);

        // THEN
        assertEquals(FULL_RESPONSE, result);
    }

    @Test
    void testCreateResponseLogTextWhenHeadersIncludedWithoutElement() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeResponseHeaders(true);
        logConfiguration.setIncludeResponsePayload(false);
        when(bufferedResponseWrapper.getStatus()).thenReturn(200);
        when(bufferedResponseWrapper.getHeaderNames()).thenReturn(HEADER_NAMES_WITHOUT_ELEMENT);

        // WHEN
        String result = underTest.createResponseLogText(bufferedResponseWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_RESPONSE_WITHOUT_ELEMENT, result);
    }

    @Test
    void testCreateResponseLogTextWhenHeadersIncludedWithOneElement() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeResponseHeaders(true);
        logConfiguration.setIncludeResponsePayload(false);
        when(bufferedResponseWrapper.getStatus()).thenReturn(200);
        when(bufferedResponseWrapper.getHeaderNames()).thenReturn(HEADER_NAMES_WITH_ONE_ELEMENT);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_1)).thenReturn(HEADER_VALUE_1);

        // WHEN
        String result = underTest.createResponseLogText(bufferedResponseWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_RESPONSE_WITH_ONE_ELEMENT, result);
    }

    @Test
    void testCreateResponseLogTextWhenHeadersIncludedWithThreeElements() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeResponseHeaders(true);
        logConfiguration.setIncludeResponsePayload(false);
        when(bufferedResponseWrapper.getStatus()).thenReturn(200);
        when(bufferedResponseWrapper.getHeaderNames()).thenReturn(HEADER_NAMES_WITH_THREE_ELEMENTS);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_1)).thenReturn(HEADER_VALUE_1);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_2)).thenReturn(HEADER_VALUE_2);
        when(bufferedResponseWrapper.getHeaders(HEADER_NAME_3)).thenReturn(HEADER_VALUE_3);

        // WHEN
        String result = underTest.createResponseLogText(bufferedResponseWrapper, logConfiguration);

        // THEN
        assertEquals(HEADER_RESPONSE_WITH_THREE_ELEMENTS, result);
    }

    @Test
    void testCreateResponseLogTextWhenPayloadIncluded() {
        // GIVEN
        LogConfiguration logConfiguration = new LogConfiguration();
        logConfiguration.setIncludeResponseHeaders(false);
        logConfiguration.setIncludeResponsePayload(true);
        when(bufferedResponseWrapper.getStatus()).thenReturn(200);
        when(bufferedResponseWrapper.getBody()).thenReturn(PAYLOAD);

        // WHEN
        String result = underTest.createResponseLogText(bufferedResponseWrapper, logConfiguration);

        // THEN
        assertEquals(PAYLOAD_RESPONSE, result);
    }
}
