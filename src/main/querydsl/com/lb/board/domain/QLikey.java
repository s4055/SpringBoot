package com.lb.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikey is a Querydsl query type for Likey
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLikey extends EntityPathBase<Likey> {

    private static final long serialVersionUID = 1311460043L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikey likey = new QLikey("likey");

    public final QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QLikey(String variable) {
        this(Likey.class, forVariable(variable), INITS);
    }

    public QLikey(Path<? extends Likey> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikey(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikey(PathMetadata metadata, PathInits inits) {
        this(Likey.class, metadata, inits);
    }

    public QLikey(Class<? extends Likey> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

