__author__ = 'hamza'

class Hypothese:
    def __init__(self, etats : set):
        self.etats = sorted(etats)

    def __contains__(self, item : str) -> bool:
        for etat in self.etats:
            if etat == item: return True
        return False

    def __eq__(self, other) -> bool:
        if not isinstance(other, Hypothese): return False
        return other.etats == self.etats

    def __len__(self):
        return len(self.etats)

    def __str__(self) -> str:
        return "{" + str(self.etats)[1:-1] + "}"
