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

import java.util.Map;

import ro.isdc.wro.model.resource.processor.ResourcePostProcessor;
import ro.isdc.wro.model.resource.processor.ResourcePreProcessor;
import ro.isdc.wro.model.resource.processor.support.ProcessorProvider;

public class IteratorProcessorProvider implements ProcessorProvider {

  @Override
  public Map<String, ResourcePreProcessor> providePreProcessors() {
    return Map.of(JSassCssProcessor.ALIAS, new JSassCssProcessor());
  }

  @Override
  public Map<String, ResourcePostProcessor> providePostProcessors() {
    return Map.of();
  }
}
