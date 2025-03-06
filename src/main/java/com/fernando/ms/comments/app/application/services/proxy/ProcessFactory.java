package com.fernando.ms.comments.app.application.services.proxy;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.application.ports.output.ExternalPostOutputPort;
import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;

public class ProcessFactory {
    private ProcessFactory() {
    }

    public static IProcess validateSaveComment( ExternalUserOutputPort externalUserOutputPort, ExternalPostOutputPort externalPostOutputPort) {
        return new RuleSaveCommentProxy(externalUserOutputPort,externalPostOutputPort);
    }

    public static IProcess validateUpdateComment(CommentPersistencePort commentPersistencePort, String id) {
        return new RuleUpdateCommentProxy(commentPersistencePort,id);
    }
}
