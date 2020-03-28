package fairyChessPack1;

import main.Trilean;

@FunctionalInterface
public interface DetachmentRequesterJudge {
	public Trilean canDetachFrom(Epifyte epifyte);
}
