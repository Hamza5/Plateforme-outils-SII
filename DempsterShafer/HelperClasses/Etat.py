__author__ = 'hamza'

idf = 1


class Etat:
    def __init__(self, text):
        self.text = text
        global idf
        self.order = idf
        idf += 1

    def __eq__(self, other):
        if not isinstance(other, Etat):
            return False
        return self.text == other.text

    def __lt__(self, other):
        if not isinstance(other, Etat):
            return False
        return self.text < other.text

    def __gt__(self, other):
        if not isinstance(other, Etat):
            return False
        return self.text > other.text

    def __le__(self, other):
        return self < other or self == other

    def __ge__(self, other):
        return self > other or self == other

    def __ne__(self, other):
        return not self == other

    def __str__(self):
        return self.text

    def id(self):
        return 'h'+str(self.order)