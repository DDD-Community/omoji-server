package almond_chocoball.omoji.app.auth.client;

public interface SocialRetrieval<T> {
    T getAttributes(String socialToken);
}
