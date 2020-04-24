package iterator.wro4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IteratorProcessorProviderTest {

  private IteratorProcessorProvider provider;

  @BeforeEach
  void setup() {
    provider = new IteratorProcessorProvider();
  }

  @Test
  @DisplayName("should provide jsass processor")
  void shouldProvideJSassProcessor() {
    assertThat(
        provider.providePreProcessors().get(JSassCssProcessor.ALIAS),
        instanceOf(JSassCssProcessor.class));
  }

  @Test
  @DisplayName("there are currently no post-processors")
  void thereAreNoPostProcessors() {
    assertThat(provider.providePostProcessors().isEmpty(), is(true));
  }
}
