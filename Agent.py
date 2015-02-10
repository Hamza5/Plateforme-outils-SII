from Hypothese import Hypothese
__author__ = 'hamza'

class Agent:
    def __init__(self, name : str, reliability : float):
        self.hypotheses = []
        self.masses = []
        self.weakings = []
        self.name = name
        self.reliability = reliability

    def add_hypothese(self, hypothese : Hypothese, masse : float, affaiblissement : float):
        self.hypotheses.append(hypothese)
        self.masses.append(masse)
        self.weakings.append(affaiblissement)

    def __eq__(self, other) -> bool:
        if not isinstance(other, Agent): return False
        return other.name == self.name

    def __contains__(self, item):
        if isinstance(item, Hypothese):
            return item in self.hypotheses
        return False

    def __str__(self) -> str:
        return self.name
