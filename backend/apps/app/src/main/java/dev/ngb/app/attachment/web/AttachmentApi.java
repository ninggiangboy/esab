package dev.ngb.app.attachment.web;

import dev.ngb.app.attachment.application.usecase.presign_attachment.dto.PresignAttachmentRequest;
import dev.ngb.app.attachment.application.usecase.presign_attachment.dto.PresignAttachmentResponse;
import dev.ngb.infrastructure.web.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Attachments", description = "Account-owned file attachments")
@RequestMapping("/api/attachments")
public interface AttachmentApi {

    @Operation(summary = "Create presigned PUT URL and persist attachment metadata")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presigned URL issued and attachment saved"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/presign")
    ResponseEntity<PresignAttachmentResponse> presign(
            @RequestBody PresignAttachmentRequest request,
            @AuthenticationPrincipal Jwt jwt
    );

    @Operation(summary = "Confirm upload finished and mark attachment available when the object exists in storage")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Attachment marked available"),
            @ApiResponse(responseCode = "400", description = "Object missing from storage",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Attachment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Attachment is not awaiting upload confirmation",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{attachmentUuid}/complete")
    ResponseEntity<Void> complete(
            @PathVariable("attachmentUuid") String attachmentUuid,
            @AuthenticationPrincipal Jwt jwt
    );
}
