package io.microlam.dynamodb;

import java.util.HashSet;
import java.util.Set;

public class ReservedWords {

	public final static Set<String> SET = new HashSet<>();
	
	static {
		SET.add("ABORT");
		SET.add("ABSOLUTE");
		SET.add("ACTION");
		SET.add("ADD");
		SET.add("AFTER");
		SET.add("AGENT");
		SET.add("AGGREGATE");
		SET.add("ALL");
		SET.add("ALLOCATE");
		SET.add("ALTER");
		SET.add("ANALYZE");
		SET.add("AND");
		SET.add("ANY");
		SET.add("ARCHIVE");
		SET.add("ARE");
		SET.add("ARRAY");
		SET.add("AS");
		SET.add("ASC");
		SET.add("ASCII");
		SET.add("ASENSITIVE");
		SET.add("ASSERTION");
		SET.add("ASYMMETRIC");
		SET.add("AT");
		SET.add("ATOMIC");
		SET.add("ATTACH");
		SET.add("ATTRIBUTE");
		SET.add("AUTH");
		SET.add("AUTHORIZATION");
		SET.add("AUTHORIZE");
		SET.add("AUTO");
		SET.add("AVG");
		SET.add("BACK");
		SET.add("BACKUP");
		SET.add("BASE");
		SET.add("BATCH");
		SET.add("BEFORE");
		SET.add("BEGIN");
		SET.add("BETWEEN");
		SET.add("BIGINT");
		SET.add("BINARY");
		SET.add("BIT");
		SET.add("BLOB");
		SET.add("BLOCK");
		SET.add("BOOLEAN");
		SET.add("BOTH");
		SET.add("BREADTH");
		SET.add("BUCKET");
		SET.add("BULK");
		SET.add("BY");
		SET.add("BYTE");
		SET.add("CALL");
		SET.add("CALLED");
		SET.add("CALLING");
		SET.add("CAPACITY");
		SET.add("CASCADE");
		SET.add("CASCADED");
		SET.add("CASE");
		SET.add("CAST");
		SET.add("CATALOG");
		SET.add("CHAR");
		SET.add("CHARACTER");
		SET.add("CHECK");
		SET.add("CLASS");
		SET.add("CLOB");
		SET.add("CLOSE");
		SET.add("CLUSTER");
		SET.add("CLUSTERED");
		SET.add("CLUSTERING");
		SET.add("CLUSTERS");
		SET.add("COALESCE");
		SET.add("COLLATE");
		SET.add("COLLATION");
		SET.add("COLLECTION");
		SET.add("COLUMN");
		SET.add("COLUMNS");
		SET.add("COMBINE");
		SET.add("COMMENT");
		SET.add("COMMIT");
		SET.add("COMPACT");
		SET.add("COMPILE");
		SET.add("COMPRESS");
		SET.add("CONDITION");
		SET.add("CONFLICT");
		SET.add("CONNECT");
		SET.add("CONNECTION");
		SET.add("CONSISTENCY");
		SET.add("CONSISTENT");
		SET.add("CONSTRAINT");
		SET.add("CONSTRAINTS");
		SET.add("CONSTRUCTOR");
		SET.add("CONSUMED");
		SET.add("CONTINUE");
		SET.add("CONVERT");
		SET.add("COPY");
		SET.add("CORRESPONDING");
		SET.add("COUNT");
		SET.add("COUNTER");
		SET.add("CREATE");
		SET.add("CROSS");
		SET.add("CUBE");
		SET.add("CURRENT");
		SET.add("CURSOR");
		SET.add("CYCLE");
		SET.add("DATA");
		SET.add("DATABASE");
		SET.add("DATE");
		SET.add("DATETIME");
		SET.add("DAY");
		SET.add("DEALLOCATE");
		SET.add("DEC");
		SET.add("DECIMAL");
		SET.add("DECLARE");
		SET.add("DEFAULT");
		SET.add("DEFERRABLE");
		SET.add("DEFERRED");
		SET.add("DEFINE");
		SET.add("DEFINED");
		SET.add("DEFINITION");
		SET.add("DELETE");
		SET.add("DELIMITED");
		SET.add("DEPTH");
		SET.add("DEREF");
		SET.add("DESC");
		SET.add("DESCRIBE");
		SET.add("DESCRIPTOR");
		SET.add("DETACH");
		SET.add("DETERMINISTIC");
		SET.add("DIAGNOSTICS");
		SET.add("DIRECTORIES");
		SET.add("DISABLE");
		SET.add("DISCONNECT");
		SET.add("DISTINCT");
		SET.add("DISTRIBUTE");
		SET.add("DO");
		SET.add("DOMAIN");
		SET.add("DOUBLE");
		SET.add("DROP");
		SET.add("DUMP");
		SET.add("DURATION");
		SET.add("DYNAMIC");
		SET.add("EACH");
		SET.add("ELEMENT");
		SET.add("ELSE");
		SET.add("ELSEIF");
		SET.add("EMPTY");
		SET.add("ENABLE");
		SET.add("END");
		SET.add("EQUAL");
		SET.add("EQUALS");
		SET.add("ERROR");
		SET.add("ESCAPE");
		SET.add("ESCAPED");
		SET.add("EVAL");
		SET.add("EVALUATE");
		SET.add("EXCEEDED");
		SET.add("EXCEPT");
		SET.add("EXCEPTION");
		SET.add("EXCEPTIONS");
		SET.add("EXCLUSIVE");
		SET.add("EXEC");
		SET.add("EXECUTE");
		SET.add("EXISTS");
		SET.add("EXIT");
		SET.add("EXPLAIN");
		SET.add("EXPLODE");
		SET.add("EXPORT");
		SET.add("EXPRESSION");
		SET.add("EXTENDED");
		SET.add("EXTERNAL");
		SET.add("EXTRACT");
		SET.add("FAIL");
		SET.add("FALSE");
		SET.add("FAMILY");
		SET.add("FETCH");
		SET.add("FIELDS");
		SET.add("FILE");
		SET.add("FILTER");
		SET.add("FILTERING");
		SET.add("FINAL");
		SET.add("FINISH");
		SET.add("FIRST");
		SET.add("FIXED");
		SET.add("FLATTERN");
		SET.add("FLOAT");
		SET.add("FOR");
		SET.add("FORCE");
		SET.add("FOREIGN");
		SET.add("FORMAT");
		SET.add("FORWARD");
		SET.add("FOUND");
		SET.add("FREE");
		SET.add("FROM");
		SET.add("FULL");
		SET.add("FUNCTION");
		SET.add("FUNCTIONS");
		SET.add("GENERAL");
		SET.add("GENERATE");
		SET.add("GET");
		SET.add("GLOB");
		SET.add("GLOBAL");
		SET.add("GO");
		SET.add("GOTO");
		SET.add("GRANT");
		SET.add("GREATER");
		SET.add("GROUP");
		SET.add("GROUPING");
		SET.add("HANDLER");
		SET.add("HASH");
		SET.add("HAVE");
		SET.add("HAVING");
		SET.add("HEAP");
		SET.add("HIDDEN");
		SET.add("HOLD");
		SET.add("HOUR");
		SET.add("IDENTIFIED");
		SET.add("IDENTITY");
		SET.add("IF");
		SET.add("IGNORE");
		SET.add("IMMEDIATE");
		SET.add("IMPORT");
		SET.add("IN");
		SET.add("INCLUDING");
		SET.add("INCLUSIVE");
		SET.add("INCREMENT");
		SET.add("INCREMENTAL");
		SET.add("INDEX");
		SET.add("INDEXED");
		SET.add("INDEXES");
		SET.add("INDICATOR");
		SET.add("INFINITE");
		SET.add("INITIALLY");
		SET.add("INLINE");
		SET.add("INNER");
		SET.add("INNTER");
		SET.add("INOUT");
		SET.add("INPUT");
		SET.add("INSENSITIVE");
		SET.add("INSERT");
		SET.add("INSTEAD");
		SET.add("INT");
		SET.add("INTEGER");
		SET.add("INTERSECT");
		SET.add("INTERVAL");
		SET.add("INTO");
		SET.add("INVALIDATE");
		SET.add("IS");
		SET.add("ISOLATION");
		SET.add("ITEM");
		SET.add("ITEMS");
		SET.add("ITERATE");
		SET.add("JOIN");
		SET.add("KEY");
		SET.add("KEYS");
		SET.add("LAG");
		SET.add("LANGUAGE");
		SET.add("LARGE");
		SET.add("LAST");
		SET.add("LATERAL");
		SET.add("LEAD");
		SET.add("LEADING");
		SET.add("LEAVE");
		SET.add("LEFT");
		SET.add("LENGTH");
		SET.add("LESS");
		SET.add("LEVEL");
		SET.add("LIKE");
		SET.add("LIMIT");
		SET.add("LIMITED");
		SET.add("LINES");
		SET.add("LIST");
		SET.add("LOAD");
		SET.add("LOCAL");
		SET.add("LOCALTIME");
		SET.add("LOCALTIMESTAMP");
		SET.add("LOCATION");
		SET.add("LOCATOR");
		SET.add("LOCK");
		SET.add("LOCKS");
		SET.add("LOG");
		SET.add("LOGED");
		SET.add("LONG");
		SET.add("LOOP");
		SET.add("LOWER");
		SET.add("MAP");
		SET.add("MATCH");
		SET.add("MATERIALIZED");
		SET.add("MAX");
		SET.add("MAXLEN");
		SET.add("MEMBER");
		SET.add("MERGE");
		SET.add("METHOD");
		SET.add("METRICS");
		SET.add("MIN");
		SET.add("MINUS");
		SET.add("MINUTE");
		SET.add("MISSING");
		SET.add("MOD");
		SET.add("MODE");
		SET.add("MODIFIES");
		SET.add("MODIFY");
		SET.add("MODULE");
		SET.add("MONTH");
		SET.add("MULTI");
		SET.add("MULTISET");
		SET.add("NAME");
		SET.add("NAMES");
		SET.add("NATIONAL");
		SET.add("NATURAL");
		SET.add("NCHAR");
		SET.add("NCLOB");
		SET.add("NEW");
		SET.add("NEXT");
		SET.add("NO");
		SET.add("NONE");
		SET.add("NOT");
		SET.add("NULL");
		SET.add("NULLIF");
		SET.add("NUMBER");
		SET.add("NUMERIC");
		SET.add("OBJECT");
		SET.add("OF");
		SET.add("OFFLINE");
		SET.add("OFFSET");
		SET.add("OLD");
		SET.add("ON");
		SET.add("ONLINE");
		SET.add("ONLY");
		SET.add("OPAQUE");
		SET.add("OPEN");
		SET.add("OPERATOR");
		SET.add("OPTION");
		SET.add("OR");
		SET.add("ORDER");
		SET.add("ORDINALITY");
		SET.add("OTHER");
		SET.add("OTHERS");
		SET.add("OUT");
		SET.add("OUTER");
		SET.add("OUTPUT");
		SET.add("OVER");
		SET.add("OVERLAPS");
		SET.add("OVERRIDE");
		SET.add("OWNER");
		SET.add("PAD");
		SET.add("PARALLEL");
		SET.add("PARAMETER");
		SET.add("PARAMETERS");
		SET.add("PARTIAL");
		SET.add("PARTITION");
		SET.add("PARTITIONED");
		SET.add("PARTITIONS");
		SET.add("PATH");
		SET.add("PERCENT");
		SET.add("PERCENTILE");
		SET.add("PERMISSION");
		SET.add("PERMISSIONS");
		SET.add("PIPE");
		SET.add("PIPELINED");
		SET.add("PLAN");
		SET.add("POOL");
		SET.add("POSITION");
		SET.add("PRECISION");
		SET.add("PREPARE");
		SET.add("PRESERVE");
		SET.add("PRIMARY");
		SET.add("PRIOR");
		SET.add("PRIVATE");
		SET.add("PRIVILEGES");
		SET.add("PROCEDURE");
		SET.add("PROCESSED");
		SET.add("PROJECT");
		SET.add("PROJECTION");
		SET.add("PROPERTY");
		SET.add("PROVISIONING");
		SET.add("PUBLIC");
		SET.add("PUT");
		SET.add("QUERY");
		SET.add("QUIT");
		SET.add("QUORUM");
		SET.add("RAISE");
		SET.add("RANDOM");
		SET.add("RANGE");
		SET.add("RANK");
		SET.add("RAW");
		SET.add("READ");
		SET.add("READS");
		SET.add("REAL");
		SET.add("REBUILD");
		SET.add("RECORD");
		SET.add("RECURSIVE");
		SET.add("REDUCE");
		SET.add("REF");
		SET.add("REFERENCE");
		SET.add("REFERENCES");
		SET.add("REFERENCING");
		SET.add("REGEXP");
		SET.add("REGION");
		SET.add("REINDEX");
		SET.add("RELATIVE");
		SET.add("RELEASE");
		SET.add("REMAINDER");
		SET.add("RENAME");
		SET.add("REPEAT");
		SET.add("REPLACE");
		SET.add("REQUEST");
		SET.add("RESET");
		SET.add("RESIGNAL");
		SET.add("RESOURCE");
		SET.add("RESPONSE");
		SET.add("RESTORE");
		SET.add("RESTRICT");
		SET.add("RESULT");
		SET.add("RETURN");
		SET.add("RETURNING");
		SET.add("RETURNS");
		SET.add("REVERSE");
		SET.add("REVOKE");
		SET.add("RIGHT");
		SET.add("ROLE");
		SET.add("ROLES");
		SET.add("ROLLBACK");
		SET.add("ROLLUP");
		SET.add("ROUTINE");
		SET.add("ROW");
		SET.add("ROWS");
		SET.add("RULE");
		SET.add("RULES");
		SET.add("SAMPLE");
		SET.add("SATISFIES");
		SET.add("SAVE");
		SET.add("SAVEPOINT");
		SET.add("SCAN");
		SET.add("SCHEMA");
		SET.add("SCOPE");
		SET.add("SCROLL");
		SET.add("SEARCH");
		SET.add("SECOND");
		SET.add("SECTION");
		SET.add("SEGMENT");
		SET.add("SEGMENTS");
		SET.add("SELECT");
		SET.add("SELF");
		SET.add("SEMI");
		SET.add("SENSITIVE");
		SET.add("SEPARATE");
		SET.add("SEQUENCE");
		SET.add("SERIALIZABLE");
		SET.add("SESSION");
		SET.add("SET");
		SET.add("SETS");
		SET.add("SHARD");
		SET.add("SHARE");
		SET.add("SHARED");
		SET.add("SHORT");
		SET.add("SHOW");
		SET.add("SIGNAL");
		SET.add("SIMILAR");
		SET.add("SIZE");
		SET.add("SKEWED");
		SET.add("SMALLINT");
		SET.add("SNAPSHOT");
		SET.add("SOME");
		SET.add("SOURCE");
		SET.add("SPACE");
		SET.add("SPACES");
		SET.add("SPARSE");
		SET.add("SPECIFIC");
		SET.add("SPECIFICTYPE");
		SET.add("SPLIT");
		SET.add("SQL");
		SET.add("SQLCODE");
		SET.add("SQLERROR");
		SET.add("SQLEXCEPTION");
		SET.add("SQLSTATE");
		SET.add("SQLWARNING");
		SET.add("START");
		SET.add("STATE");
		SET.add("STATIC");
		SET.add("STATUS");
		SET.add("STORAGE");
		SET.add("STORE");
		SET.add("STORED");
		SET.add("STREAM");
		SET.add("STRING");
		SET.add("STRUCT");
		SET.add("STYLE");
		SET.add("SUB");
		SET.add("SUBMULTISET");
		SET.add("SUBPARTITION");
		SET.add("SUBSTRING");
		SET.add("SUBTYPE");
		SET.add("SUM");
		SET.add("SUPER");
		SET.add("SYMMETRIC");
		SET.add("SYNONYM");
		SET.add("SYSTEM");
		SET.add("TABLE");
		SET.add("TABLESAMPLE");
		SET.add("TEMP");
		SET.add("TEMPORARY");
		SET.add("TERMINATED");
		SET.add("TEXT");
		SET.add("THAN");
		SET.add("THEN");
		SET.add("THROUGHPUT");
		SET.add("TIME");
		SET.add("TIMESTAMP");
		SET.add("TIMEZONE");
		SET.add("TINYINT");
		SET.add("TO");
		SET.add("TOKEN");
		SET.add("TOTAL");
		SET.add("TOUCH");
		SET.add("TRAILING");
		SET.add("TRANSACTION");
		SET.add("TRANSFORM");
		SET.add("TRANSLATE");
		SET.add("TRANSLATION");
		SET.add("TREAT");
		SET.add("TRIGGER");
		SET.add("TRIM");
		SET.add("TRUE");
		SET.add("TRUNCATE");
		SET.add("TTL");
		SET.add("TUPLE");
		SET.add("TYPE");
		SET.add("UNDER");
		SET.add("UNDO");
		SET.add("UNION");
		SET.add("UNIQUE");
		SET.add("UNIT");
		SET.add("UNKNOWN");
		SET.add("UNLOGGED");
		SET.add("UNNEST");
		SET.add("UNPROCESSED");
		SET.add("UNSIGNED");
		SET.add("UNTIL");
		SET.add("UPDATE");
		SET.add("UPPER");
		SET.add("URL");
		SET.add("USAGE");
		SET.add("USE");
		SET.add("USER");
		SET.add("USERS");
		SET.add("USING");
		SET.add("UUID");
		SET.add("VACUUM");
		SET.add("VALUE");
		SET.add("VALUED");
		SET.add("VALUES");
		SET.add("VARCHAR");
		SET.add("VARIABLE");
		SET.add("VARIANCE");
		SET.add("VARINT");
		SET.add("VARYING");
		SET.add("VIEW");
		SET.add("VIEWS");
		SET.add("VIRTUAL");
		SET.add("VOID");
		SET.add("WAIT");
		SET.add("WHEN");
		SET.add("WHENEVER");
		SET.add("WHERE");
		SET.add("WHILE");
		SET.add("WINDOW");
		SET.add("WITH");
		SET.add("WITHIN");
		SET.add("WITHOUT");
		SET.add("WORK");
		SET.add("WRAPPED");
		SET.add("WRITE");
		SET.add("YEAR");
		SET.add("ZONE");
	}
	
	public static boolean isReserved(String word) {
		return SET.contains(word.toUpperCase());
	}
}