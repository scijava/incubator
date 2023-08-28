package org.scijava.ops.engine.yaml;

import static org.scijava.ops.engine.yaml.YAMLUtils.subMap;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.scijava.ops.api.Hints;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.features.YAMLOpInfoCreator;
import org.scijava.struct.ItemIO;
import org.scijava.struct.Member;
import org.scijava.struct.MemberInstance;
import org.scijava.struct.Struct;

/**
 * An abstract base class for parsing the YAML into values common to {@link OpInfo}s.
 *
 * @author Gabriel Selzer
 */
public abstract class AbstractYAMLOpInfoCreator implements YAMLOpInfoCreator {

    @Override
    public OpInfo create(final URI identifier, final Map<String, Object> yaml) {
        // Parse source - start after the leading slash
        final String srcString = identifier.getPath().substring(1);
        // TODO: Parse version
        final String version = "0.0";
//        final String version = path.substring(path.indexOf('/') + 1);
        final Map<String, Object> tags = subMap(yaml, "tags");
        // Parse names
        final String[] names;
        if (tags.containsKey("name")) {
            names = new String[]{(String) tags.get("name")};
        } else {
            var tmp = tags.get("names");
            if (tmp instanceof List) {
                names = ((List<String>) tmp).toArray(String[]::new);
            }
            else if (tmp instanceof String) {
                names = new String[] {(String) tmp};
            }
            else {
                throw new IllegalArgumentException("Cannot convert" + tmp + "to a String[]!");
            }
        }
        for (int i = 0; i < names.length; i++) {
            names[i] = names[i].trim();
        }
        // Parse priority
        double priority = 0.0;
        if (tags.containsKey("priority")) {
            Object p = tags.get("priority");
            if (p instanceof Number) priority = ((Number) p).doubleValue();
            else if (p instanceof String) {
                priority = Double.parseDouble((String) p);
            } else {
                throw new IllegalArgumentException("Op priority not parsable");
            }
        }
        // Create the OpInfo
        OpInfo info;
        try {
            info = create(srcString, names, priority, null, version, tags);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (tags.containsKey("parameters")) {
            List<Map<String, Object>> params =
                (List<Map<String, Object>>) tags.get("parameters");
            Iterator<Map<String, Object>> paramItr = params.iterator();

            List<Member<?>> members = info.struct().members();
            for (int i = 0; i < members.size(); i++) {
                Member<?> m = members.get(i);
                if (m.isInput() || m.isOutput()) {
                    if (!paramItr.hasNext()) break;
                    Map<String, Object> paramMap = paramItr.next();
                    members.set(i, wrapMember.apply(m, paramMap));
                }
            }
        }

        return info;
    }

    static final Set<String> outputKeys = new HashSet<>(
        List.of("OUTPUT, CONTAINER, MUTABLE"));

    private final BiFunction<Member<?>, Map<String, Object>, Member<?>>
        wrapMember = (member, map) -> {
        String name = member.getKey();
        if (member.isInput() && !member.isOutput()) {
            name = (String) map.get("INPUT");
        }
        else {
            for (String key: outputKeys) {
                if (map.containsKey(key)) {
                    name = (String) map.get(key);
                    break;
                }
            }
        }
        String desc = ((String) map.getOrDefault("description", "")).trim();
        return new RenamedMember<>(member, name, desc);
    };

    abstract OpInfo create(final String identifier, final String[] names, final double priority, final Hints hints, final String version, Map<String, Object> yaml) throws Exception;

    private class RenamedMember<T> implements Member<T> {

        private final Member<T> src;
        private final String name;
        private final String desc;

        public RenamedMember(final Member<T> src, final String name, final String desc) {
            this.src = src;
            this.name = name;
            this.desc = desc;
        }

        @Override public String getKey() {
            return this.name;
        }

        @Override public String getDescription() {
            return this.desc;
        }

        @Override public Type getType() {
            return src.getType();
        }

        @Override public Class<T> getRawType() {
            return src.getRawType();
        }

        @Override public ItemIO getIOType() {
            return src.getIOType();
        }

        @Override public boolean isInput() {
            return src.isInput();
        }

        @Override public boolean isOutput() {
            return src.isOutput();
        }

        @Override public boolean isStruct() {
            return src.isStruct();
        }

        @Override public boolean isRequired() {
            return src.isRequired();
        }

        @Override public Struct childStruct() {
            return src.childStruct();
        }

        @Override public MemberInstance<T> createInstance(Object o) {
            return src.createInstance(o);
        }
    }
}
