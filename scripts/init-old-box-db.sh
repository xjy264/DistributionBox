#!/usr/bin/env bash
set -euo pipefail

DB_HOST="${DB_HOST:-127.0.0.1}"
DB_PORT="${DB_PORT:-3306}"
DB_USER="${DB_USER:-root}"
DB_PASS="${DB_PASS:-}"
DB_NAME="${DB_NAME:-shujia}"

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
MIGRATIONS_DIR="$ROOT_DIR/sql/migrations"
SEED_SQL="$ROOT_DIR/sql/old-box-seed.sql"

MYSQL_CMD=(mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER")
if [[ -n "$DB_PASS" ]]; then
  MYSQL_CMD+=("-p$DB_PASS")
fi

"${MYSQL_CMD[@]}" -e "CREATE DATABASE IF NOT EXISTS $DB_NAME DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

while IFS= read -r f; do
  echo "Applying $(basename "$f")"
  "${MYSQL_CMD[@]}" "$DB_NAME" < "$f"
done < <(find "$MIGRATIONS_DIR" -maxdepth 1 -name 'V*.sql' | sort -V)

echo "Applying seed: $(basename "$SEED_SQL")"
"${MYSQL_CMD[@]}" "$DB_NAME" < "$SEED_SQL"

echo "Done. Database '$DB_NAME' is ready."
