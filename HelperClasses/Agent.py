from HelperClasses.Hypothese import Hypothese

__author__ = 'hamza'

idf = 1


class Agent:
    def __init__(self, name: str, reliability: float):
        self.hypotheses = []
        self.masses = []
        self.weakings = []
        self.name = name
        self.reliability = reliability
        global idf
        self.order = idf
        self.disabled = False
        idf += 1

    def add_hypothese(self, hypothese: Hypothese, masse: float, affaiblissement: float):
        self.hypotheses.append(hypothese)
        self.masses.append(masse)
        self.weakings.append(affaiblissement)

    def remove_hypothese(self, hypothese: Hypothese):
        index = self.hypotheses.index(hypothese)
        del self.hypotheses[index]
        del self.masses[index]
        del self.weakings[index]

    def __eq__(self, other) -> bool:
        if not isinstance(other, Agent):
            return False
        return other.name == self.name

    def __contains__(self, item):
        if isinstance(item, Hypothese):
            return item in self.hypotheses
        return False

    def __iter__(self):
        return zip(self.hypotheses, self.masses, self.weakings)

    def __str__(self) -> str:
        return self.name

    def id(self):
        return 'a'+str(self.order)

    def disable(self, yes: bool):
        self.disabled = yes