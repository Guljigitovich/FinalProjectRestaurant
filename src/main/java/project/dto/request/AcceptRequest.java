package project.dto.request;

public record AcceptRequest(
    Long userId,
    Boolean accept
) {
}
