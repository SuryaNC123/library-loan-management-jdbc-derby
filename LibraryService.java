package benchmark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import connection.connectionManager;

public class PerformanceEvaluator {

    // NORMAL INSERT TEST
    public void normalInsertTest(int count) {

        long startTime;
        long endTime;

        String query =
                "INSERT INTO Books(title, isbn, available) "
                + "VALUES (?, ?, ?)";

        try (
            Connection con =
                    connectionManager.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);
        ) {

            startTime = System.nanoTime();

            for (int i = 1; i <= count; i++) {

                ps.setString(1,
                        "Normal Book " + i);

                ps.setString(2,
                        "NISBN" + i);

                ps.setBoolean(3, true);

                ps.executeUpdate();
            }

            endTime = System.nanoTime();

            long totalTime =
                    (endTime - startTime)
                    / 1000000;

            System.out.println(
                    "\nNORMAL INSERT TEST");

            System.out.println(
                    "Records Inserted: "
                    + count);

            System.out.println(
                    "Execution Time: "
                    + totalTime
                    + " ms");

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // BATCH INSERT TEST
    public void batchInsertTest(int count) {

        long startTime;
        long endTime;

        String query =
                "INSERT INTO Books(title, isbn, available) "
                + "VALUES (?, ?, ?)";

        try (
            Connection con =
                    connectionManager.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);
        ) {

            startTime = System.nanoTime();

            for (int i = 1; i <= count; i++) {

                ps.setString(1,
                        "Batch Book " + i);

                ps.setString(2,
                        "BISBN" + i);

                ps.setBoolean(3, true);

                ps.addBatch();
            }

            ps.executeBatch();

            endTime = System.nanoTime();

            long totalTime =
                    (endTime - startTime)
                    / 1000000;

            System.out.println(
                    "\nBATCH INSERT TEST");

            System.out.println(
                    "Records Inserted: "
                    + count);

            System.out.println(
                    "Execution Time: "
                    + totalTime
                    + " ms");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
 // STATEMENT TEST
    public void statementTest(int count) {

        long startTime;
        long endTime;

        try (
            Connection con =
                    connectionManager.getConnection();

            Statement stmt =
                    con.createStatement();
        ) {

            startTime = System.nanoTime();

            for (int i = 1; i <= count; i++) {

                String query =
                        "INSERT INTO Books(title, isbn, available) "
                        + "VALUES ('Statement Book "
                        + i
                        + "', 'SISBN"
                        + i
                        + "', true)";

                stmt.executeUpdate(query);
            }

            endTime = System.nanoTime();

            long totalTime =
                    (endTime - startTime)
                    / 1000000;

            System.out.println(
                    "\nSTATEMENT TEST");

            System.out.println(
                    "Records Inserted: "
                    + count);

            System.out.println(
                    "Execution Time: "
                    + totalTime
                    + " ms");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
 // PREPARED STATEMENT TEST
    public void preparedStatementTest(int count) {

        long startTime;
        long endTime;

        String query =
                "INSERT INTO Books(title, isbn, available) "
                + "VALUES (?, ?, ?)";

        try (
            Connection con =
                    connectionManager.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);
        ) {

            startTime = System.nanoTime();

            for (int i = 1; i <= count; i++) {

                ps.setString(1,
                        "Prepared Book " + i);

                ps.setString(2,
                        "PISBN" + i);

                ps.setBoolean(3, true);

                ps.executeUpdate();
            }

            endTime = System.nanoTime();

            long totalTime =
                    (endTime - startTime)
                    / 1000000;

            System.out.println(
                    "\nPREPARED STATEMENT TEST");

            System.out.println(
                    "Records Inserted: "
                    + count);

            System.out.println(
                    "Execution Time: "
                    + totalTime
                    + " ms");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
 // FULL TABLE SCAN TEST
    public void fullTableScanTest() {

        long startTime;
        long endTime;

        String query =
                "SELECT * FROM Books "
                + "WHERE title = 'Java Programming'";

        try (
            Connection con =
                    connectionManager.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);
        ) {

            startTime = System.nanoTime();

            ps.executeQuery();

            endTime = System.nanoTime();

            long totalTime =
                    (endTime - startTime);

            System.out.println(
                    "\nFULL TABLE SCAN TEST");

            System.out.println(
                    "Execution Time: "
                    + totalTime
                    + " ns");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
 // INDEXED QUERY TEST
    public void indexedQueryTest() {

        long startTime;
        long endTime;

        String query =
                "SELECT * FROM Books "
                + "WHERE isbn = 'ISBN101'";

        try (
            Connection con =
                    connectionManager.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);
        ) {

            startTime = System.nanoTime();

            ps.executeQuery();

            endTime = System.nanoTime();

            long totalTime =
                    (endTime - startTime);

            System.out.println(
                    "\nINDEXED QUERY TEST");

            System.out.println(
                    "Execution Time: "
                    + totalTime
                    + " ns");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
    public void perOperationCommitTest(int count) {

        String query =
                "INSERT INTO Members(name, active_loans) "
                + "VALUES (?, ?)";

        try (
                Connection con =
                        connectionManager.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            con.setAutoCommit(false);

            long start = System.nanoTime();

            for (int i = 1; i <= count; i++) {

                ps.setString(1,
                        "PerCommitMember" + System.nanoTime());

                ps.setInt(2, 0);

                ps.executeUpdate();

                // Commit every operation
                con.commit();
            }

            long end = System.nanoTime();

            System.out.println(
                    "\n===== PER-OPERATION COMMIT TEST ====="
            );

            System.out.println(
                    "Execution Time: "
                    + ((end - start) / 1000000.0)
                    + " ms"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public void batchedCommitTest(int count) {

        String query =
                "INSERT INTO Members(name, active_loans) "
                + "VALUES (?, ?)";

        try (
                Connection con =
                        connectionManager.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            con.setAutoCommit(false);

            long start = System.nanoTime();

            for (int i = 1; i <= count; i++) {

                ps.setString(1,
                        "BatchCommitMember" + System.nanoTime());

                ps.setInt(2, 0);

                ps.executeUpdate();
            }

            // Single commit
            con.commit();

            long end = System.nanoTime();

            System.out.println(
                    "\n===== BATCHED COMMIT TEST ====="
            );

            System.out.println(
                    "Execution Time: "
                    + ((end - start) / 1000000.0)
                    + " ms"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public void warmUpBenchmark() {

        System.out.println("\nWarming up JVM and Derby cache...");

        for (int i = 0; i < 1000; i++) {

            Math.sqrt(i);
        }

        System.out.println("Warm-up completed.");
    }
    public void generatePerformanceReport() {

        System.out.println(
                "\n================================================="
        );

        System.out.println(
                "            PERFORMANCE REPORT"
        );

        System.out.println(
                "================================================="
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Operation",
                "Observation"
        );

        System.out.println(
                "-------------------------------------------------"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Normal Insert",
                "Slower"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Batch Insert",
                "Faster"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Statement Query",
                "Slower"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "PreparedStatement Query",
                "Faster"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Full Table Scan",
                "Slow"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Indexed Query",
                "Fast"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Per-operation Commit",
                "Slow"
        );

        System.out.printf(
                "%-35s %-15s\n",
                "Batched Commit",
                "Fast"
        );

        System.out.println(
                "================================================="
        );
    }
}