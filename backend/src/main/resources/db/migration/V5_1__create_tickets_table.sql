-- tickets table
-- Postgres
CREATE TABLE tickets (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ticket_key          VARCHAR(20) NOT NULL,
    org_id              UUID NOT NULL REFERENCES organizations(id),
    title               VARCHAR(255) NOT NULL,
    description         TEXT NULL,
    status              VARCHAR(32) NOT NULL,
    assignee_user_id    UUID NULL REFERENCES users(id) ON DELETE SET NULL,
    created_by_user_id  UUID NOT NULL REFERENCES users(id),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_tickets_status
        CHECK (status IN ('OPEN','IN_PROGRESS','RESOLVED','CLOSED')),
    CONSTRAINT uq_tickets_ticket_key UNIQUE (ticket_key)
);

CREATE INDEX idx_org_id_created_at ON tickets(org_id, created_at DESC);
CREATE INDEX idx_org_id_status_created_at ON tickets(org_id, status, created_at DESC);
CREATE INDEX idx_org_id_assignee_created_at ON tickets(org_id, assignee_user_id, created_at DESC);

CREATE TRIGGER trg_tickets_set_updated_at
    BEFORE UPDATE ON tickets
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
