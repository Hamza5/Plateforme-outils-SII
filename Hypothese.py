import sys
from Etat import Etat

__author__ = 'hamza'


class Hypothese:
    def __init__(self, états: list):
        self.états = sorted(états)

    def __contains__(self, item: Etat) -> bool:
        for état in self.états:
            if état == item:
                return True
        return False

    def __eq__(self, other) -> bool:
        if not isinstance(other, Hypothese):
            return False
        return other.états == self.états

    def __len__(self) -> int:
        return len(self.états)

    def __str__(self) -> str:
        return "{" + str([str(e) for e in self.états])[1:-1] + "}"

    def id(self):
        # print(self.états, file=sys.stderr)
        # for x in self.états:
        #     print(x, type(x), file=sys.stderr)
        #     print(x.id(), type(x.id()), file=sys.stderr)
        return '-'.join([x.id() for x in self.états])