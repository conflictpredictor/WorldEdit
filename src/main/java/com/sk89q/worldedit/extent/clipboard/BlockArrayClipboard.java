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

package com.sk89q.worldedit.extent.clipboard;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Stores block data as a multi-dimensional array of {@link BaseBlock}s and
 * other data as lists or maps.
 */
public class BlockArrayClipboard implements Clipboard {

    private final Region region;
    private Vector offset = new Vector();
    private final BaseBlock[][][] blocks;
    private final List<ClipboardEntity> entities = new ArrayList<ClipboardEntity>();

    /**
     * Create a new instance.
     *
     * @param region the bounding region
     */
    public BlockArrayClipboard(Region region) {
        checkNotNull(region);
        checkNotNull(offset);
        this.region = region.clone();

        Vector dimensions = getDimensions();
        blocks = new BaseBlock[dimensions.getBlockX()][dimensions.getBlockY()][dimensions.getBlockZ()];
    }

    @Override
    public Region getRegion() {
        return region.clone();
    }

    @Override
    public Vector getOffset() {
        return offset;
    }

    @Override
    public void setOffset(Vector offset) {
        checkNotNull(offset);
        this.offset = offset;
    }

    /**
     * Get the dimensions of the copy, which is at minimum (1, 1, 1).
     *
     * @return the dimensions
     */
    private Vector getDimensions() {
        return region.getMaximumPoint().subtract(region.getMinimumPoint()).add(1, 1, 1);
    }

    @Override
    public Vector getMinimumPoint() {
        return region.getMinimumPoint();
    }

    @Override
    public Vector getMaximumPoint() {
        return region.getMaximumPoint();
    }

    @Override
    public List<Entity> getEntities() {
        return new ArrayList<Entity>(entities);
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity) {
        ClipboardEntity ret = new ClipboardEntity(location, entity);
        entities.add(ret);
        return ret;
    }

    @Override
    public BaseBlock getBlock(Vector position) {
        if (region.contains(position)) {
            Vector v = position.subtract(region.getMinimumPoint());
            BaseBlock block = blocks[v.getBlockX()][v.getBlockY()][v.getBlockZ()];
            if (block != null) {
                return new BaseBlock(block);
            }
        }

        return new BaseBlock(BlockID.AIR);
    }

    @Override
    public BaseBlock getLazyBlock(Vector position) {
        return getBlock(position);
    }

    @Override
    public boolean setBlock(Vector position, BaseBlock block) throws WorldEditException {
        if (region.contains(position)) {
            Vector v = position.subtract(region.getMinimumPoint());
            blocks[v.getBlockX()][v.getBlockY()][v.getBlockZ()] = new BaseBlock(block);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Operation commit() {
        return null;
    }

    /**
     * Stores entity data.
     */
    private class ClipboardEntity extends StoredEntity {
        ClipboardEntity(Location location, BaseEntity entity) {
            super(location, entity);
        }

        @Override
        public boolean remove() {
            return entities.remove(this);
        }
    }

}
