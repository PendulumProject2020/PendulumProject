//Code adapted from user zabuza@stackOverflow
package main;
public class Quintuple<A, B, C, D, E> {
    private final A mFirst;
    private final B mSecond;
    private final C mThird;
    private final D mFourth;
    private final E mFifth;

    public Quintuple(final A first, final B second, final C third, final D fourth, final E fifth) {
        this.mFirst = first;
        this.mSecond = second;
        this.mThird = third;
        this.mFourth = fourth;
        this.mFifth = fifth;
    }

    public A getFirst() {
        return this.mFirst;
    }

    public B getSecond() {
        return this.mSecond;
    }

    public C getThird() {
        return this.mThird;
    }
    
    public D getFourth() {
        return this.mFourth;
    }
    
    public E getFifth() {
        return this.mFifth;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.mFirst.hashCode();
        result = prime * result + this.mSecond.hashCode();
        result = prime * result + this.mThird.hashCode();
        result = prime * result + this.mFourth.hashCode();
        result = prime * result + this.mFifth.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quintuple other = (Quintuple) obj;
        if (this.mFirst == null) {
            if (other.mFirst != null) {
                return false;
            }
        } else if (!this.mFirst.equals(other.mFirst)) {
            return false;
        }
        if (this.mSecond == null) {
            if (other.mSecond != null) {
                return false;
            }
        } else if (!this.mSecond.equals(other.mSecond)) {
            return false;
        }
        if (this.mThird == null) {
            if (other.mThird != null) {
                return false;
            }
        } else if (!this.mThird.equals(other.mThird)) {
            return false;
        }
        if (this.mFourth == null) {
            if (other.mFourth != null) {
                return false;
            }
        } else if (!this.mFourth.equals(other.mFourth)) {
            return false;
        }
        if (this.mFifth == null) {
            if (other.mFifth != null) {
                return false;
            }
        } else if (!this.mFifth.equals(other.mFifth)) {
            return false;
        }
        return true;
    }
}