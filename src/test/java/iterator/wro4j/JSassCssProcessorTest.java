package iterator.wro4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.bit3.jsass.Compiler;
import io.bit3.jsass.Options;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;

class JSassCssProcessorTest {

  private Compiler compiler;

  private Options options;

  private JSassCssProcessor processor;

  @BeforeEach
  void setup() {
    compiler = new Compiler();
    options = new Options();
    processor = new JSassCssProcessor(compiler, options);
  }

  @Test
  @DisplayName("should return null given null resource when process")
  void shouldReturnNull_givenNullResource_whenProcess() throws Exception {
    // given
    Reader reader = new StringReader("foo");
    Writer writer = new StringWriter();
    // when
    processor.process(null, reader, writer);
    // then
    assertThat(writer.toString(), is(emptyOrNullString()));
  }

  @Test
  @DisplayName("should write css given sass when process")
  void shouldWriteCss_givenSass_whenProcess() throws Exception {
    // given
    Reader reader = new StringReader(resource("input-01.scss"));
    Writer writer = new StringWriter();
    Resource resource = new Resource();
    resource.setType(ResourceType.CSS);
    resource.setUri("/foo");
    // when
    processor.process(resource, reader, writer);
    // then
    String expected = resource("expected-output-01.css");
    assertThat(writer.toString().trim(), equalTo(expected.trim()));
  }

  @Test
  @DisplayName("should throw given invalid sass when process")
  void shouldThrow_givenInvalidSass_whenProcess() throws Exception {
    // given
    Reader reader = new StringReader("<!DOCTYPE html>");
    Writer writer = new StringWriter();
    Resource resource = new Resource();
    resource.setType(ResourceType.CSS);
    resource.setUri("/foo");
    // then
    assertThrows(WroRuntimeException.class, () -> processor.process(resource, reader, writer));
  }

  private static String resource(String name) throws Exception {
    return IOUtils.toString(
        iterator.wro4j.JSassCssProcessorTest.class.getResourceAsStream("/iterator/wro4j/" + name),
        StandardCharsets.UTF_8);
  }
}
