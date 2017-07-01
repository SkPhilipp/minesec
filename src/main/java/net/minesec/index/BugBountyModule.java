package net.minesec.index;

import com.fasterxml.jackson.core.JsonGenerator;
import net.minesec.core.Module;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Copyright (c) 30/06/2017, MineSec. All rights reserved.
 */
public class BugBountyModule extends Module {

    private BugBountyIndexer bugBountyIndexer;

    public static void main(String[] args) throws IOException {
        BugBountyModule bugBountyModule = new BugBountyModule();
        bugBountyModule.execute();
    }

    public BugBountyModule() {
        this.bugBountyIndexer = new BugBountyIndexer();
    }

    @Override
    public void execute() throws IOException {
        try(JsonGenerator generator = this.openGenerator()) {
            generator.writeStartArray();
            Consumer<BugBounty> bugBountyConsumer = bounty -> {
                try {
                    generator.writeObject(bounty);
                } catch (IOException e) {
                    exit(e);
                }
            };
            bugBountyIndexer.indexBugcrowdCurated(bugBountyConsumer);
            bugBountyIndexer.indexBugcrowdPrograms(bugBountyConsumer);
            bugBountyIndexer.indexVulnerabilityLabCurated(bugBountyConsumer);
            bugBountyIndexer.indexHackeroneCurated(bugBountyConsumer);
            generator.writeEndArray();
        }
    }

}
