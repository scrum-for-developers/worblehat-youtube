
package de.codecentric.worblehat.acceptancetests.step;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.ScenarioType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Kontext eines Szenarios. Intern wird ein ThreadLocal verwendet, um fuer jeden ausfuehrenden Thread eine eigene
 * Instanz zu halten. Dieses Verhalten ist ueber die Getter und Setter transparent.
 */
@Component("context")
public class StoryContext {

	private static final ThreadLocal<StoryContext> context = new ContextLocal();
	private Map<String, String> keyvalues = new HashMap<>();
	private Map<String, Object> objects = new HashMap<>();

	public static StoryContext getContextForCurrentThread() {
		return context.get();
	}

	private StoryContext self() {
		return getContextForCurrentThread();
	}

	@AfterScenario(uponType = ScenarioType.EXAMPLE)
	public void reset() {
		context.set(new StoryContext());
	}

	public void put(String key, String value) {
		self().keyvalues.put(key, value);
	}

	public String get(String key) {
		return self().keyvalues.get(key);
	}

	public void putObject(String key, Object value) {
		self().objects.put(key, value);
	}

	public Object getObject(String key) {
		return self().objects.get(key);
	}

	public Map<String, String> getKeyValueMap() {
		return self().keyvalues;
	}

	public Map<String, Object> getObjectValueMap() {
		return self().objects;
	}

	public void updateWithMap(Map<String, String> replacements) {
		for (Entry<String, String> replacementEntry : replacements.entrySet()) {
			String key = replacementEntry.getKey();
			String value = replacementEntry.getValue();
			self().put(key, value);
		}
	}

	public void updateWithObjectMap(Map<String, Object> replacements) {
		for (Entry<String, Object> replacementEntry : replacements.entrySet()) {
			String key = replacementEntry.getKey();
			Object value = replacementEntry.getValue();
			self().putObject(key, value);
		}
	}

	public void updateWithContext(StoryContext context2) {
		updateWithMap(context2.getKeyValueMap());
		updateWithObjectMap(context2.getObjectValueMap());
	}

	static final class ContextLocal extends ThreadLocal<StoryContext> {
		@Override
		protected StoryContext initialValue() {
			return new StoryContext();
		}

	}

}
