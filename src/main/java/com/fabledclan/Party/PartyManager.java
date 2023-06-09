package com.fabledclan.Party;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PartyManager {
    private static Map<Player, Party> parties = new HashMap<>();
    private static Map<Player, Party> pendingInvites = new HashMap<>();
                    //invitee, Party they were invited to

    public static Party getParty(Player player) {
        return parties.get(player);
    }

    public static void addParty(Party party) {
        for (Player member : party.getMembers()) {
            parties.put(member, party);
        }
    }

    public static void addPlayerToParty(Player player, Party party) {
        parties.put(player, party);
        party.addMember(player);
        PartyScoreboard partyScoreboard = new PartyScoreboard(party);
        player.setScoreboard(partyScoreboard.getScoreboard());
    }
    

    public static void removePlayerFromParty(Player player) {
        Party party = getParty(player);
        if (party != null) {
            party.removeMember(player);
            parties.remove(player);
        }
    }

    public static void disbandParty(Party party) {
        for (Player member : party.getMembers()) {
            parties.remove(member);
        }
        party.disband();
    }

    public static void addPendingInvite(Player invitee, Party party) {
        pendingInvites.put(invitee, party);
    }
    
    public static Party getPendingInvite(Player invitee) {
        return pendingInvites.get(invitee);
    }
    
    public static void removePendingInvite(Player invitee) {
        pendingInvites.remove(invitee);
    }

    public static Player getPartyLeader(Party party)
    {
        return party.getLeader();
    
    }

}
