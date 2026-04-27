package amble.tron.core.entities.lighttrail;

import net.minecraft.nbt.NbtCompound;
import org.joml.Vector4f;

import java.util.Arrays;

public class Trail {
    public final float[] buffer;
    public final int size;
    public final float gray;
    public int lastIndex;
    public int entries;
    public int nullEntries;

    public Trail(int length) {
        this(length, 1.0f);
    }

    public Trail(int length, float gray) {
        buffer = new float[7 * length];
        size = length;
        this.gray = gray;
    }

    public void add(Vector4f first, Vector4f second, float alpha) {
        // Add config option for disabling if laggy

        if (alpha <= 0.0) {
            nullEntries++;
        } else {
            nullEntries = 0;
        }

        if (nullEntries < size) {
            int i = lastIndex * 7;
            buffer[i] = first.x;
            buffer[i + 1] = first.y;
            buffer[i + 2] = first.z;
            buffer[i + 3] = second.x;
            buffer[i + 4] = second.y;
            buffer[i + 5] = second.z;
            buffer[i + 6] = alpha;
        }

        lastIndex = (lastIndex + 1) % size;
        entries++;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("LastIndex", this.lastIndex);
        nbt.putInt("Entries", this.entries);
        nbt.putInt("NullEntries", this.nullEntries);
        for (int i = 0; i < this.buffer.length; i++) {
            nbt.putFloat("B" + i, this.buffer[i]);
        }
        return nbt;
    }

    public void fromNbt(NbtCompound nbt) {
        for (int i = 0; i < this.buffer.length; i++) {
            this.buffer[i] = nbt.getFloat("B" + i);
        }

        this.lastIndex = nbt.getInt("LastIndex");
        this.entries = nbt.getInt("Entries");
        this.nullEntries = nbt.getInt("NullEntries");
    }

    public void clear() {
        Arrays.fill(this.buffer, 0.0f);
        this.lastIndex = 0;
        this.entries = 0;
        this.nullEntries = 0;
    }
}