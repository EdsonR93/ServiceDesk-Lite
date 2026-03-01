--Ticket comments table
CREATE TABLE ticket_comments
(
    id             UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    org_id         UUID        NOT NULL REFERENCES organizations (id),
    ticket_id      UUID        NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    author_user_id UUID        NOT NULL REFERENCES users (id),
    body           TEXT        NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_ticket_comments_org_ticket_created_at ON ticket_comments (org_id, ticket_id, created_at ASC);
