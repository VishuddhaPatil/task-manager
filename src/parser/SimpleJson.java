
package parser;

public class SimpleJson {

    private SimpleJson() {
    }

    public static String getString(String json, String key) {

        String search = "\"" + key + "\"";
        int keyIndex = json.indexOf(search);

        if (keyIndex == -1) {
            return null;
        }

        int colonIndex = json.indexOf(":", keyIndex);
        if (colonIndex == -1) {
            return null;
        }

        int start = colonIndex + 1;

        while (start < json.length()
                && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        if (json.startsWith("null", start)) {
            return null;
        }

        if (start >= json.length() || json.charAt(start) != '"') {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (int i = start + 1; i < json.length(); i++) {

            char c = json.charAt(i);

            if (escaped) {
                switch (c) {
                    case 'n': result.append('\n'); break;
                    case 'r': result.append('\r'); break;
                    case 't': result.append('\t'); break;
                    case '"': result.append('"'); break;
                    case '\\': result.append('\\'); break;
                    case '/': result.append('/'); break;
                    default: result.append(c);
                }

                escaped = false;

            } else if (c == '\\') {
                escaped = true;

            } else if (c == '"') {
                return result.toString();

            } else {
                result.append(c);
            }
        }

        throw new IllegalArgumentException("Invalid JSON string");
    }

    public static String extractOutputText(String response) {

        int outputIndex = response.indexOf("\"type\":\"output_text\"");

        if (outputIndex == -1) {
            outputIndex = response.indexOf("\"type\": \"output_text\"");
        }

        if (outputIndex == -1) {
            throw new IllegalArgumentException(
                    "OpenAI response contains no output text"
            );
        }

        String outputSection = response.substring(outputIndex);

        return getString(outputSection, "text");
    }
}
