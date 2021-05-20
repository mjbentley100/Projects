"""

"""

import random

def main():

    menu_c = "0"

    #todo, random message
    print("Welcome to the 1984 Arcade")
    print("Smile, you're on camera")

    while (menu_c != "5"):
        print("_________________")
        print("0) Random Game")
        print("1) Hangman")
        print("2) Minesweeper")
        print("3) Stats")
        print("4) About")
        print("5) Exit")
        print("_________________")
        menu_c = input("Please pick an option: ")
        print("")
        print("")
        print("")

        if (menu_c == "1"):
            hangman_start()
            while (1 == 1):
                tumble = input("Would you like to play again (y/n): ")
                if (tumble == "n"):
                    break
                elif (tumble == "y"):
                    hangman_start()
                else:
                    print("invalid input")
        elif (menu_c == "2"):
            mine_input = input("Please enter width, height, and # of mines example (5 5 3): ")
            tump = mine_input.split(" ")
            minesweeper_start(int(tump[0]), int(tump[1]), int(tump[2]))
        elif (menu_c == "3"):
            stats()
        elif (menu_c == "4"):
            print("Terminal Arcade Pack Developed by Mason Bentley for CSE107 2019")
        elif (menu_c == "0"):
            john = random.randint(0, 1)
            if (john == 0):
                hangman_start()
            if (john == 1):
                mine_input = input("Please enter width, height, and # of mines example (5 5 3): ")
                tump = mine_input.split(" ")
                minesweeper_start(int(tump[0]), int(tump[1]), int(tump[2]))



"""
Hangman Functions
"""

def hangman_start():
    #print(choose_word())
    word = choose_word()
    attempts = 6
    incorrect_g = []
    score = 0
    usr_input = ""
    dsp_word = []

    for i in range(len(word)):
        dsp_word.append("*")

    num_a = len(word)

    print("Hangman")
    print("_________________")

    while (1 == 1):
        print("score: " + str(score))
        print("Incorrect guesses left: " + str(attempts))
        print("Incorrect guessed letters: ", end = "")
        print_list(incorrect_g)
        print(" ")
        stickman(attempts)
        print(" ")

        print_list(dsp_word)

        while (1 == 1):
            usr_input = input("please enter a guess: ").lower()
            if (check_valid(usr_input) == 1):
                break
            else:
                print("Invalid Input, please try again")

        dsp_word = word_cmp(word, dsp_word, usr_input)


        if (num_a == check_ast(dsp_word)):
            attempts -= 1
            incorrect_g.append(usr_input)
            score -= 2
        else:
            num_a = check_ast(dsp_word)
            score += 2

        if (check_win(dsp_word) == 1):
            print_list(dsp_word)
            print("You Win!")
            print_list(dsp_word)
            f = open("hangman", "a")
            f.write("win " + str(score) + "\n")
            f.close()
            break
        if (attempts <= 0):
            stickman(attempts)
            print("You Lose :(")
            f = open("hangman", "a")
            f.write("lose " + str(score) + "\n")
            f.close()
            break

def check_ast(lst):
    num = 0
    for i in range(len(lst)):
        if (lst[i] == "*"):
            num += 1
    return num

def print_list(lst):
    for i in range(len(lst)):
        print(lst[i], end = "")
    print("")

def choose_word():
    words = ["noxious", "intelegent", "gas", "gone", "else", "race", "fog", "under", "carrot", "kitten", "everywhere", "rascal"]
    index = random.randint(0, len(words)-1)
    return words[index]

def word_cmp(word1, word2, letter):
    for i in range(len(word1)):
        if (word1[i] == letter):
            word2[i] = letter
    return word2

def check_win(word):
    win = 1
    for i in range(len(word)):
        if (word[i] == "*"):
            win = 0
    return win

def check_valid(letter):
    valid = 1
    if (len(letter) > 1 or len(letter) < 1):
        valid = 0
    if (letter.isalpha() == False):
        valid = 0
    return valid

def stickman(tries):
    stuck = [" O\n", "/", "|", "\\\n", "/", " \\\n"]
    for i in range(6 - tries):
        print(stuck[i], end = "")



"""
Minesweeper Functions
"""

def minesweeper_start(width, height, mines):

    board = gen_board(width, height, mines)
    empty_board = gen_empty(width, height)
    usr_input = ""
    x = 0
    y = 0
    splt = []
    score = 0


    print("welcome to minesweeper")
    print("Commands")
    print('"select x y" - select cell at x,y')
    print('"flag x y" - place flag at x,y')

    flags = mines

    while (1 == 1):
        print("")
        print_board(empty_board)
        print("")
        while (1 == 1):
            usr_input = input("> ")
            splt = usr_input.split(" ")
            if (splt[0] == "select"):
                x = int(splt[1])
                y = int(splt[2])

                if (empty_board[x][y] == "f"):
                    break

                empty_board[x][y] = board[x][y]
                score += 1
                break
            elif (splt[0] == "flag" and flags > 0):
                x = int(splt[1])
                y = int(splt[2])
                empty_board[x][y] = "f"
                flags -= 1
                score += 1
                break
            else:
                print("invalid input")
        if (board[x][y] == "*" and splt[0] == "select"):
            print("You Lose")
            f = open("minesweeper", "a")
            f.write("lose " + str(score) + "\n")
            f.close()
            break
        if (check_win_m(empty_board) == 1):
            print("You Win!")
            f = open("minesweeper", "a")
            f.write("win " + str(score) + "\n")
            f.close()
            break


def check_win_m(board):
    win = 1
    for i in range(len(board)):
        for j in range(len(board[i])):
            if (board[i][j] == "O"):
                win = 0
    return win


def gen_empty(x, y):
    board = []
    for i in range(x):
        board.append([])
        for j in range(y):
            board[i].append("O")
    return board

def gen_board(x, y, m):
    board = []
    for i in range(x):
        board.append([])
        for j in range(y):
            board[i].append(0)

    for i in range(m):

        while (1 == 1):
            p = random.randint(0, x-1)
            q = random.randint(0, y-1)
            if (board[p][q] != "*"):
                board[p][q] = "*"
                break

        for k in range(3):
            for l in range(3):
                if ((p-k) + 1 >= 0 and (p-k) + 1 < x and (q-l) + 1 >= 0 and (q-l) + 1 < y):
                    if (board[(p-k) + 1][(q-l) + 1] != "*"):
                        board[(p-k) + 1][(q-l) + 1] += 1

    for i in range(x):
        for j in range(y):
            if (board[i][j] == 0):
                board[i][j] = "_"
    return board

def print_board(board):
    for i in range(len(board)):
        print_list(board[i])



def stats():
    tmp = []
    win_h = 0
    loss_h = 0
    high_h = -2000
    low_h = 2000
    win_m = 0
    loss_m = 0
    high_m = 0
    low_m = 2000

    print("Hangman Stats")
    print("_______________")
    h = open("hangman", "r")
    for line in h:
        tmp = line.split(" ")
        if (tmp[0] == "win"):
            win_h += 1
        else:
            loss_h += 1
        if (int(tmp[1]) > high_h):
            high_h = int(tmp[1])
        if (int(tmp[1]) < low_h):
            low_h = int(tmp[1])
    h.close()

    print("High Score: " + str(high_h))
    print("Low Score: " + str(low_h))
    print(str((win_h/(loss_h + win_h)) * 100) + "% wins " + str((loss_h/(loss_h + win_h)) * 100) + "% losses")

    print("")
    print("")

    print("Minesweeper Stats")
    print("_______________")
    m = open("minesweeper", "r")
    for line in m:
        tmp = line.split(" ")
        if (tmp[0] == "win"):
            win_m += 1
        else:
            loss_m += 1
        if (int(tmp[1]) > high_m):
            high_m = int(tmp[1])
        if (int(tmp[1]) < low_m):
            low_m = int(tmp[1])
    m.close()

    print("High Score: " + str(high_m))
    print("Low Score: " + str(low_m))
    print(str((win_m/(loss_m + win_m)) * 100) + "% wins " + str((loss_m/(loss_m + win_m)) * 100) + "% losses")


if __name__ == "__main__":
    main()





















#whitespace
