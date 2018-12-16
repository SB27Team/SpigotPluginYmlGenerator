package team.sb27.spigotymlgenerator.util;

public final class StringUtil {

    public interface Joiner<T> {
        String join(T obj);
    }

    public static <T> String join(Iterable<T> iterable, Joiner<T> joiner, String seperator){
        StringBuilder builder = new StringBuilder();
        for (T element : iterable)
            builder.append(joiner.join(element)).append(seperator);
        String result = builder.toString();
        if(result.endsWith(seperator)) return result.substring(0, result.length()-seperator.length());
        return result;
    }

    public static <T> String join(T[] array, Joiner<T> joiner, String seperator){
        StringBuilder builder = new StringBuilder();
        for (T element : array)
            builder.append(joiner.join(element)).append(seperator);
        String result = builder.toString();
        if(result.endsWith(seperator)) return result.substring(0, result.length()-seperator.length());
        return result;
    }

}
