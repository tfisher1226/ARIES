package org.aries.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UID implements Cloneable, Serializable {
	
    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(UID.class);
    
    
    public UID() {
        hostAddr = null;
        process = 0;
        sec = 0;
        other = 0;
        _hashValue = -1;
        _valid = false;
        _stringForm = null;
        _byteForm = null;

        try
        {
            hostAddr = Utility.hostInetAddr(); /* calculated only once */
            process = Utility.getpid();

            if (UID.initTime == 0)
            	UID.initTime = (int) (System.currentTimeMillis() / 1000);

            sec = UID.initTime;

            other = UID.getValue();

            _valid = true;

            generateHash();
        }
        catch (UnknownHostException e) {
            log.warn("Cannot get local host");
            _valid = false;
        }
    }

    /**
     * Create a copy of the specified identifier.
     */

    public UID(UID copyFrom) {
        copy(copyFrom);
    }

    public UID(byte[] byteForm) {
        if (byteForm == null)
            throw new IllegalArgumentException();
        
        hostAddr = new long[2];
        _hashValue = -1;
        _stringForm = null;
        _byteForm = null;
        
        try
        {
            ByteArrayInputStream ba = new ByteArrayInputStream(byteForm);
            DataInputStream ds = new DataInputStream(ba);
            
            hostAddr[0] = ds.readLong();
            hostAddr[1] = ds.readLong();
            process = ds.readInt();
            sec = ds.readInt();
            other = ds.readInt();
            
            _valid = true;
        } catch (Throwable e) {
            log.warn("Failed to create Uid from bytes", e);
            _valid = false;
        }
        
        generateHash();
    }
    
    /**
     * Create UID from string representation. If the string does not represent a
     * valid UID then the instance will be set to nullUID.
     */

    public UID(String uidString)
    {
        this(uidString, true);
    }

    /**
     * Create UID from string representation. boolean arg says whether to give
     * up if an error is detected or to simply replace with nullUID.
     */

    public UID(String uidString, boolean errsOk)
    {
        if (uidString == null)
            throw new IllegalArgumentException();
        
        char theBreakChar = UID.getBreakChar(uidString);

        hostAddr = new long[2];
        process = 0;
        sec = 0;
        other = 0;
        _hashValue = -1;
        _valid = false;
        _stringForm = null;
        _byteForm = null;
        
        if (uidString.length() > 0)
        {
            int startIndex = 0;
            int endIndex = 0;
            String s = null;

            try
            {
                while (uidString.charAt(endIndex) != theBreakChar)
                    endIndex++;

                s = uidString.substring(startIndex, endIndex);
                hostAddr[0] = Utility.hexStringToLong(s);

                startIndex = endIndex + 1;
                endIndex++;
                while (uidString.charAt(endIndex) != theBreakChar)
                    endIndex++;

                s = uidString.substring(startIndex, endIndex);
                hostAddr[1] = Utility.hexStringToLong(s);

                startIndex = endIndex + 1;
                endIndex++;

                while (uidString.charAt(endIndex) != theBreakChar)
                    endIndex++;

                s = uidString.substring(startIndex, endIndex);
                process = (int) Utility.hexStringToLong(s);

                startIndex = endIndex + 1;
                endIndex++;

                while (uidString.charAt(endIndex) != theBreakChar)
                    endIndex++;

                s = uidString.substring(startIndex, endIndex);
                sec = (int) Utility.hexStringToLong(s);

                s = uidString.substring(endIndex + 1, uidString.length());
                other = (int) Utility.hexStringToLong(s);

                _valid = true;

                generateHash();
            } catch (NumberFormatException e) {
                if (!errsOk) {
                    log.warn(uidString, e);
                }

                _valid = false;
            } catch (StringIndexOutOfBoundsException e) {
                if (!errsOk) {
                    log.warn(uidString, e);
                }

                _valid = false;
            }
            catch (Throwable e) {
                log.warn(uidString, e);
                _valid = false;
            }
        } else {
            this.copy(UID.nullUID());
        }

        if (!_valid) {
            if (errsOk) {
                try {
                    /*
                     * We do this so the instance is printable, but
                     * it shouldn't be usable from here on in!
                     */
                    
                    this.copy(UID.nullUID());
                    
                    _valid = false;
                } catch (Exception e) {
                    log.fatal("Error: "+uidString, e);
                    throw new Error("Error: "+uidString, e);
                }
            } else {
                throw new Error("Error: "+uidString);
            }
        }
    }

    public UID(long[] addr, int processId, int time, int incr)
    {
        try
        {
            hostAddr = new long[2];
            hostAddr[0] = addr[0];
            hostAddr[1] = addr[1];

            process = processId;
            sec = time;
            other = incr;

            _valid = true;

            generateHash();
        } catch (Throwable e) {
            _valid = false;
            throw new Error("Could not recreate UID", e);
        }
    }

    /**
     * Override Object.hashCode. We always return a positive value.
     */

    /*
     * generateHash should have been called by now.
     */

    public int hashCode ()
    {
        return _hashValue;
    }

    /**
     * Print a human-readable form of the UID.
     */

    public void print (PrintStream strm)
    {
        strm.print("<UID:" + this.toString() + ">");
    }

    public String stringForm ()
    {
        // no need to synchronize since object is immutable

        if (_stringForm == null)
            _stringForm = Utility.longToHexString(hostAddr[0]) + UID.breakChar
                    + Utility.longToHexString(hostAddr[1]) + UID.breakChar
                    + Utility.intToHexString(process) + UID.breakChar
                    + Utility.intToHexString(sec) + UID.breakChar
                    + Utility.intToHexString(other);

        return _stringForm;
    }

    /**
     * @return a string representation of the UID with all : replaced by _ so
     *         that the name may be used as a file name.
     */

    public String fileStringForm ()
    {
        return Utility.longToHexString(hostAddr[0]) + UID.fileBreakChar
                + Utility.longToHexString(hostAddr[1]) + UID.fileBreakChar
                + Utility.intToHexString(process) + UID.fileBreakChar
                + Utility.intToHexString(sec) + UID.fileBreakChar
                + Utility.intToHexString(other);
    }
    
    /**
     * Get the byte representation of the UID. Useful for packing and creating other
     * representations of transaction ids.
     * 
     * @return the byte array. Cached once created.
     */
    
    public byte[] getBytes ()
    {
        /*
         * We should only really be doing this once, so overhead should
         * be negligible.
         */
        
        if (_byteForm == null)
        {
            ByteArrayOutputStream ba = new ByteArrayOutputStream(UID_SIZE);
            DataOutputStream ds = new DataOutputStream(ba);
            
            try
            {
                ds.writeLong(hostAddr[0]);
                ds.writeLong(hostAddr[1]);
                ds.writeInt(process);
                ds.writeInt(sec);
                ds.writeInt(other);
                //_byteForm = stringForm().getBytes("UTF-8");
                
                _byteForm = ba.toByteArray();
            }
            catch (Throwable e) {
                log.warn(e);
                _byteForm = null;
            }
        }

        return _byteForm;
    }

    /**
     * Same as stringForm()
     */

    public String toString ()
    {
        return stringForm();
    }

    // return the process id value in hex form.
    // The internal format is UIDs mostly should not be exposed, but some
    // recovery/expiry code need this.
    public String getHexPid ()
    {
        return Utility.intToHexString(process);
    }

    /**
     * Create a copy of this instance.
     */

    public Object clone () throws CloneNotSupportedException
    {
        return new UID(this);
    }

    /**
     * Copy the specified UID over this instance.
     */

    private void copy (UID toCopy)
    {
        if (toCopy == this)
            return;

        hostAddr = toCopy.hostAddr;
        process = toCopy.process;
        sec = toCopy.sec;
        other = toCopy.other;
        _hashValue = toCopy._hashValue;
        _valid = toCopy._valid;
    }

    /**
     * UID comparisons.
     */

    /**
     * Override Object.equals
     */

    public boolean equals (Object o)
    {
        if (o instanceof UID)
            return this.equals((UID) o);
        else
            return false;
    }

    public boolean equals (UID u)
    {
        if (u == null)
            return false;

        if (u == this)
            return true;

        return ((other == u.other) && (sec == u.sec) && (process == u.process)
                && (hostAddr[0] == u.hostAddr[0]) && (hostAddr[1] == u.hostAddr[1]));
    }

    public boolean notEquals (UID u)
    {
        if (u == null)
            return true;

        if (u == this)
            return false;

        return ((other != u.other) || (sec != u.sec) || (process != u.process)
                || (hostAddr[0] != u.hostAddr[0]) || (hostAddr[1] != u.hostAddr[1]));
    }

    public boolean lessThan (UID u)
    {
        if (u == null)
            return false;

        if (u == this)
            return false;

        if (this.equals(u))
            return false;

        if (LAST_RESOURCE_UID.equals(this))
            return false;

        if (LAST_RESOURCE_UID.equals(u))
            return true;

        if ((hostAddr[0] < u.hostAddr[0]) && (hostAddr[1] < u.hostAddr[1]))
            return true;
        else
        {
            if ((hostAddr[0] == u.hostAddr[0])
                    && (hostAddr[1] == u.hostAddr[1]))
            {
                if (process < u.process)
                    return true;
                else if (process == u.process)
                {
                    if (sec < u.sec)
                        return true;
                    else if ((sec == u.sec) && (other < u.other))
                        return true;
                }
            }
        }

        return false;
    }

    public boolean greaterThan (UID u)
    {
        if (u == null)
            return false;

        if (u == this)
            return false;

        if (this.equals(u))
            return false;

        if (LAST_RESOURCE_UID.equals(this))
            return true;

        if (LAST_RESOURCE_UID.equals(u))
            return false;

        if ((hostAddr[0] > u.hostAddr[0]) && (hostAddr[1] > u.hostAddr[1]))
            return true;
        else
        {
            if ((hostAddr[0] == u.hostAddr[0])
                    && (hostAddr[1] == u.hostAddr[0]))
            {
                if (process > u.process)
                    return true;
                else if (process == u.process)
                {
                    if (sec > u.sec)
                        return true;
                    else if ((sec == u.sec) && (other > u.other))
                        return true;
                }
            }
        }

        return false;
    }

    /**
     * Is the UID valid?
     */

    public final boolean valid ()
    {
        return _valid;
    }

    /**
     * Return a null UID (0:0:0:0:0)
     */

    public static final UID nullUID ()
    {
        return NIL_UID;
    }

    /**
     * Return a last resource UID (0:0:0:0:1)
     */
    public static final UID lastResourceUID ()
    {
        return LAST_RESOURCE_UID;
    }

    /**
     * Return the maximum UID (7fffffff:7fffffff:7fffffff:7fffffff:7fffffff)
     */
    public static final UID maxUID ()
    {
        return MAX_UID;
    }

    /**
     * Return the minimum UID
     * (-80000000:-80000000:-80000000:-80000000:-80000000)
     */
    public static final UID minUID ()
    {
        return MIN_UID;
    }

    /*
     * Serialization methods. Similar to buffer packing. If the UID is invalid
     * the an IOException is thrown.
     * @since JTS 2.1.
     */

    private void writeObject (java.io.ObjectOutputStream out)
            throws IOException
    {
        if (_valid)
        {
            out.writeLong(hostAddr[0]);
            out.writeLong(hostAddr[1]);
            out.writeInt(process);
            out.writeInt(sec);
            out.writeInt(other);
        }
        else
            throw new IOException("Invalid UID object.");
    }

    /*
     * Serialization methods. Similar to buffer unpacking. If the
     * unserialization fails then an IOException is thrown.
     * @since JTS 2.1.
     */

    private void readObject (java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException
    {
        try
        {
            hostAddr = new long[2];
            
            hostAddr[0] = in.readLong();
            hostAddr[1] = in.readLong();
            process = in.readInt();
            sec = in.readInt();
            other = in.readInt();

            _valid = true; // should not be able to pack an invalid UID.

            generateHash();
        }
        catch (IOException e)
        {
            _valid = false;

            throw e;
        }
    }

    private static final int MAX_SEQ_VALUE = 0x40000000; // 2^30, which is a bit
                                                         // conservative.

    private static int getValue ()
    {
        int value = 0;
        do
        {
            value = uidsCreated.getAndIncrement();
            if (value == MAX_SEQ_VALUE)
            {
                uidsCreated.set(0);
                initTime = (int) (System.currentTimeMillis() / 1000);
            }
        }
        while (value >= MAX_SEQ_VALUE);

        return value;
    }

    /*
     * Is an idempotent operation, so can be called more than once without
     * adverse effect.
     */

    private final void generateHash ()
    {
        if (_valid)
        {
            if (true)
                _hashValue = (int) hostAddr[0] ^ (int) hostAddr[1] ^ process
                        ^ sec ^ other;
            else
            {
                int g = 0;
                String p = toString();
                int len = p.length();
                int index = 0;

                if (len > 0)
                {
                    while (len-- > 0)
                    {
                        _hashValue = (_hashValue << 4)
                                + (int) (p.charAt(index));
                        g = _hashValue & 0xf0000000;

                        if (g > 0)
                        {
                            _hashValue = _hashValue ^ (g >> 24);
                            _hashValue = _hashValue ^ g;
                        }

                        index++;
                    }
                }
            }

            if (_hashValue < 0)
                _hashValue = -_hashValue;
        } else {
            log.warn("generateHash called for invalid UID");
        }
    }

    /*
     * Since we may be given a UID from the file system (which uses '_' to
     * separate fields, we need to be able to convert.
     */

    private static final char getBreakChar (String uidString)
    {
        if (uidString == null)
            return UID.breakChar;

        if (uidString.indexOf(fileBreakChar) != -1)
            return UID.fileBreakChar;
        else
            return UID.breakChar;
    }

    protected volatile long[] hostAddr; // representation of ipv6 address (and
                                        // ipv4)

    protected volatile int process;

    protected volatile int sec;

    protected volatile int other;

    private volatile int _hashValue;

    private volatile boolean _valid;

    private volatile String _stringForm;
    
    private volatile byte[] _byteForm;

    private static final AtomicInteger uidsCreated = new AtomicInteger();

    private static volatile int initTime;

    private static final char breakChar = ':';

    private static final char fileBreakChar = '_';

    private static final UID NIL_UID = new UID("0:0:0:0:0");

    private static final UID LAST_RESOURCE_UID = new UID("0:0:0:0:1");

    private static final UID MAX_UID = new UID(
            "7fffffff:7fffffff:7fffffff:7fffffff:7fffffff");

    private static final UID MIN_UID = new UID(
            "-80000000:-80000000:-80000000:-80000000:-80000000");
    
    private static final int UID_SIZE = 2*8 + 3*4; // in bytes
}
