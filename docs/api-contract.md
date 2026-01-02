# API Contract Notes

Use this document to capture:
- endpoint conventions
- pagination format
- error response format
- auth headers

## Conventions (recommended)
- Auth header: `Authorization: Bearer <token>`
- Pagination: `page`, `size`, `sort`
- Error format: consistent JSON with `code`, `message`, `details`
