package com.fabledclan.Party;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;

public class Party {
    private Player leader;
    private List<Player> members;

    public Party(Player leader) {
        this.leader = leader;
        this.members = new ArrayList<>();
        this.members.add(leader);
    }

    public void sendInvite(Player invitee) {
        invitee.sendMessage(ChatColor.GREEN + "You have been invited to join a party. Type accept to join or decline to decline.");
        PartyManager.addPendingInvite(invitee, this);
    }
    
    

    public void addMember(Player player) {
        this.members.add(player);
    }

    public void removeMember(Player player) {
        this.members.remove(player);
    }

    public void disband() {
        this.members.clear();
    }

    public Player getLeader() {
        return leader;
    }

    public List<Player> getMembers() {
        return members;
    }
}
