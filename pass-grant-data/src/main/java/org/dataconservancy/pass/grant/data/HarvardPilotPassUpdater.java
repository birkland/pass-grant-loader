package org.dataconservancy.pass.grant.data;

public class HarvardPilotPassUpdater extends DefaultPassUpdater {

    public HarvardPilotPassUpdater () {
        super(new DefaultPassEntityUtil());
        super.setDomain("harvard.edu");
    }

}
