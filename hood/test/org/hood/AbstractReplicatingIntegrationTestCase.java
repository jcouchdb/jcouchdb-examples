package org.hood;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.ReplicationInfo;
import org.jcouchdb.db.Server;
import org.jcouchdb.exception.CouchDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that can restore a database to its previous state by replicating the database
 * to a random new database on the same server, than executing the tests and then
 * deleting the database, replicating it back and then deleting the backup.
 * 
 * This is not the fastest way to restore a state, but works in any case.
 * 
 * @author shelmberger
 *
 */
public abstract class AbstractReplicatingIntegrationTestCase
    extends AbstractAppIntegrationTestCase
{
    private static Logger log = LoggerFactory
        .getLogger(AbstractReplicatingIntegrationTestCase.class);
    
    private String backupDb;

    /**
     * Returns the database to replicate / restore
     * @return
     */
    protected abstract Database getDatabaseToRestore();
    
    
    @Override
    protected void onSetUp() throws Exception
    {
        super.onSetUp();

        Database db = getDatabaseToRestore();
        Server server = db.getServer();
            
        backupDb = "testbackup-" + server.getUUIDs(1).get(0);
        
        server.createDatabase(backupDb);
        
        log.debug("Replicating {} to {}", db, backupDb);
        
        ReplicationInfo info = server.replicate(db.getName(), backupDb, false);
        
        if (!info.isOk())
        {
            throw new CouchDBException("Error replicating " + db.getName()+ " to " + backupDb + ": " + info);
        }
    }
    
    @Override
    protected void onTearDown() throws Exception
    {
        Database db = getDatabaseToRestore();
        Server server = db.getServer();
        log.debug("Deleting {}", db);
        String dbName = db.getName();
        server.deleteDatabase(dbName);
        server.createDatabase(dbName);
        log.debug("Restoring {} to {}", db, backupDb);
        server.replicate(backupDb, dbName, false);
        server.deleteDatabase(backupDb);
    }
}
