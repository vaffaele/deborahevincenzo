package raf.sollecito.config;

import org.hibernate.dialect.MySQL55Dialect;
// import org.hibernate.dialect.MySQL5InnoDBDialect; // Deprecated

// public class MySQL5InnoDBDialectUtf8mb4 extends MySQL5InnoDBDialect {
public class MySQL55DialectUtf8mb4 extends MySQL55Dialect {
  @Override
  public String getTableTypeString() {
    return "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci";
  }
}
