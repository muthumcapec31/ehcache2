/**
 *  Copyright 2003-2010 Terracotta, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.ehcache.terracotta;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.cluster.CacheCluster;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.store.Store;
import net.sf.ehcache.transaction.local.SoftLockFactory;
import net.sf.ehcache.transaction.local.TransactionIDFactory;
import net.sf.ehcache.transaction.xa.EhcacheXAStore;
import net.sf.ehcache.writer.writebehind.WriteBehind;

/**
 * Factory for creating clustered instances
 *
 * @author Tim Eck
 * @author Geert Bevin
 * @since 1.7
 */
public interface ClusteredInstanceFactory {

    /**
     * Create a Store instance for the given cache
     *
     * @param cache the cache will backed by the returned store
     * @return store instance
     */
    Store createStore(Ehcache cache);

    /**
     * Get an api for looking at the clustered node topology.
     */
    CacheCluster getTopology();

    /**
     * Create an WriteBehind instance for the given cache
     *
     * @param cache the cache to which the write behind will be tied
     * @return write behind instance
     */
    WriteBehind createWriteBehind(Ehcache cache);

    /**
     * @param cache
     * @param store
     * @return return clustered instance of EhcacheXAStore
     */
    EhcacheXAStore createXAStore(Ehcache cache, Store store, boolean bypassValidation);

    /**
     * Create a replicator for the cache events of a given cache
     *
     * @param cache the cache to which the replicator will be bound
     * @return cache event replicator
     */
    CacheEventListener createEventReplicator(Ehcache cache);
    
    /**
     * Returns a universally unique identifiers for this factory.
     * 
     * @return the identifier as a string
     */
    String getUUID();
    
    /**
     * Cleans up any resources left behind after the shutdown of the associated CacheManager
     */
    void shutdown();

    TransactionIDFactory createTransactionIDFactory(String clusterUUID);

    SoftLockFactory getOrCreateSoftLockFactory(String cacheName);
}
