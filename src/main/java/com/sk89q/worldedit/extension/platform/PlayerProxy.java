/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.extension.platform;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldVector;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;

import static com.google.common.base.Preconditions.checkNotNull;

class PlayerProxy extends AbstractPlayerActor {

    private final Player basePlayer;
    private final Actor permActor;
    private final World world;

    PlayerProxy(Player basePlayer, Actor permActor, World world) {
        checkNotNull(basePlayer);
        checkNotNull(permActor);
        checkNotNull(world);
        this.basePlayer = basePlayer;
        this.permActor = permActor;
        this.world = world;
    }

    @Override
    public int getItemInHand() {
        return basePlayer.getItemInHand();
    }

    @Override
    public void giveItem(int type, int amount) {
        basePlayer.giveItem(type, amount);
    }

    @Override
    public BlockBag getInventoryBlockBag() {
        return basePlayer.getInventoryBlockBag();
    }

    @Override
    public String getName() {
        return basePlayer.getName();
    }

    @Override
    public BaseEntity getState() {
        throw new UnsupportedOperationException("Can't getState() on a player");
    }

    @Override
    public Location getLocation() {
        return basePlayer.getLocation();
    }

    @Override
    public WorldVector getPosition() {
        return basePlayer.getPosition();
    }

    @Override
    public double getPitch() {
        return basePlayer.getPitch();
    }

    @Override
    public double getYaw() {
        return basePlayer.getYaw();
    }

    @Override
    public void setPosition(Vector pos, float pitch, float yaw) {
        basePlayer.setPosition(pos, pitch, yaw);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void printRaw(String msg) {
        basePlayer.printRaw(msg);
    }

    @Override
    public void printDebug(String msg) {
        basePlayer.printDebug(msg);
    }

    @Override
    public void print(String msg) {
        basePlayer.print(msg);
    }

    @Override
    public void printError(String msg) {
        basePlayer.printError(msg);
    }

    @Override
    public String[] getGroups() {
        return permActor.getGroups();
    }

    @Override
    public boolean hasPermission(String perm) {
        return permActor.hasPermission(perm);
    }
}
