# Sample DB Schema

- users(id, name, email, oauth_id, role)
- groups_tbl(id, name)
- memberships(id, user_id, group_id)
- expenses(id, title, amount, created_by_id, group_id, created_at, version)
- settlements(id, payer_id, receiver_id, amount, expense_id, created_at)
