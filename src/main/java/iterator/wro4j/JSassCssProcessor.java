/**
 * Copyright © 2020 Iterator Ltd. (iteratoruk@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iterator.wro4j;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Compiler;
import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import io.bit3.jsass.context.StringContext;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.SupportedResourceType;
import ro.isdc.wro.model.resource.processor.ResourcePreProcessor;

@SupportedResourceType(ResourceType.CSS)
public class JSassCssProcessor implements ResourcePreProcessor {

  public static final String ALIAS = "jsass";

  private final Compiler compiler;

  private final Options options;

  public JSassCssProcessor() {
    this(new Compiler(), new Options());
  }

  public JSassCssProcessor(Compiler compiler, Options options) {
    this.compiler = compiler;
    this.options = options;
  }

  @Override
  public void process(Resource resource, Reader reader, Writer writer) throws IOException {
    if (resource == null) {
      return;
    }
    String sass = IOUtils.toString(reader);
    try {
      URI uri = new URI(resource.getUri());
      StringContext ctx = new StringContext(sass, uri, null, options);
      Output out = compiler.compile(ctx);
      writer.write(out.getCss());
    } catch (CompilationException | URISyntaxException e) {
      throw new WroRuntimeException("Unable to compile SASS", e);
    }
  }
}
