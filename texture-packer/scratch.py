import itertools

# Maps Neighbor Index to Possible Tile Modifier from
# matching Neighbor Value
MOD_TABLE = [
#	 0,    1,    2,    3,    4,    5,    6,    7,    8,    9,   10,   11,   12,   13
	[-1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1],	# 0
	[ 0,    0,    0,    1,    1,    1,    1,    1,    1,    1,    1,    0,    0,   -1],	# 1
	[-1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1],	# 2
	[ 0,    1,    1,    0,    1,    1,    0,    1,    1,    1,    0,    1,    0,   -1],	# 3
	[ 1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    0],	# 4
	[ 1,    1,    0,    1,    1,    0,    1,    1,    0,    0,    1,    0,    1,   -1],	# 5
	[-1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1],	# 6
	[ 1,    1,    1,    1,    1,    1,    0,    0,    0,    0,    0,    1,    1,   -1],	# 7
	[-1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1],	# 8
]

def isNotValid(neighbors):
	if neighbors[4] != 1:
		return 0
	if neighbors[1] == 0 and neighbors[7] == 0:
		return 1
	if neighbors[3] == 0 and neighbors[5] == 0:
		return 1
	return 0

def getModifier(neighbors):
	matchTable = range(14)
	for index in range(9):
		matches = [match for match in range(14) if (\
			(MOD_TABLE[index][match] == -1) or \
			(MOD_TABLE[index][match] == neighbors[index]) )]
		matchTable = [match for match in matchTable if match in matches]
		if len(matchTable) == 0:
			return -1

	# Smaller values have priority in the case of multiple matches
	return min(matchTable)

def printMap(neighbors):
	assert len(neighbors) == 9

	nst = [str(i) for i in neighbors]
	msg = "\n".join([" | ".join(nst[y:y+3]) for y in range(0, 9, 3)])

	print msg


if __name__ == "__main__":
	choices = [0, 1]
	failures = []
	for combo in itertools.product(choices, repeat=9):
		if isNotValid(list(combo)) == 1:
			continue
		mod = getModifier(list(combo))
		if mod == -1:
			printMap(list(combo))
			print ""