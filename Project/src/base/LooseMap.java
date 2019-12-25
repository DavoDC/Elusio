package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A less strict hash map where retrievals can occur with key substrings and the
 * character case of keys is ignored. The key type is always String
 *
 * @author David
 * @param <V>
 */
public class LooseMap<V> extends HashMap<String, V> {

    /**
     * Retrieve a mapping with key substring whose character case is ignored
     *
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {

        // Get lowercase key substring
        String subKey = ((String) key).toLowerCase();

        // Get all keys
        Set<String> keys = keySet();

        // For all keys
        for (String curKey : keys) {

            // If lower case key contains key substring
            if (curKey.toLowerCase().contains(subKey)) {

                // Return object linked to key
                return super.get(curKey);
            }
        }

        // If nothing was found, throw error
        throw new IllegalArgumentException("LooseMap get error");
    }

    /**
     * Get all values stored in this map
     *
     * @return
     */
    public Object[] valArray() {

        // Get all keys
        Object[] keys = keySet().toArray();

        // Initialize holder 
        ArrayList<V> values = new ArrayList<>();

        // Add to holder
        // For every key
        for (int i = 0; i < size(); i++) {

            // Add value linked to key
            values.add(get((String) keys[i]));
        }

        // Return holder
        return values.toArray();
    }

}
