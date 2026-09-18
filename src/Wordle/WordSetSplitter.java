package Wordle;

import java.util.ArrayList;

public class WordSetSplitter {

	private static final int WORD_LENGTH = 5;
	private final ArrayList<String>[][] wordsByLetterAndPosition;
	private final ArrayList<String>[] wordsWithDoubleLetter;

	@SuppressWarnings("unchecked")
	public WordSetSplitter() {
		wordsByLetterAndPosition = new ArrayList[26][WORD_LENGTH];
		wordsWithDoubleLetter = new ArrayList[26];
		for (int letter = 0; letter < 26; letter++) {
			wordsWithDoubleLetter[letter] = new ArrayList<>();
			for (int position = 0; position < WORD_LENGTH; position++) {
				wordsByLetterAndPosition[letter][position] = new ArrayList<>();
			}
		}
	}

	public WordSetSplitter(ArrayList<String> words) {
		this();
		addWords(words);
	}

	public void addWords(Iterable<String> words) {
		if (words == null) {
			return;
		}

		for (String word : words) {
			if (word == null || word.length() != WORD_LENGTH) {
				continue;
			}

			String normalizedWord = word.toLowerCase();
			boolean[] countedDoubleLetters = new boolean[26];
			for (int position = 0; position < WORD_LENGTH; position++) {
				char letter = normalizedWord.charAt(position);
				if (letter >= 'a' && letter <= 'z') {
					ArrayList<String> wordsAtPosition =
							wordsByLetterAndPosition[letter - 'a'][position];
					if (!wordsAtPosition.contains(normalizedWord)) {
						wordsAtPosition.add(normalizedWord);
					}

					for (int nextPosition = position + 1;
							nextPosition < WORD_LENGTH; nextPosition++) {
						if (letter == normalizedWord.charAt(nextPosition)
								&& !countedDoubleLetters[letter - 'a']) {
							if (!wordsWithDoubleLetter[letter - 'a'].contains(normalizedWord)) {
								wordsWithDoubleLetter[letter - 'a'].add(normalizedWord);
							}
							countedDoubleLetters[letter - 'a'] = true;
						}
					}
				}
			}
		}
	}

	public ArrayList<String> getWordsWithLetterAt(char letter, int position) {
		if (position < 0 || position >= WORD_LENGTH) {
			throw new IndexOutOfBoundsException("Position must be between 0 and 4");
		}

		letter = Character.toLowerCase(letter);
		if (letter < 'a' || letter > 'z') {
			return new ArrayList<>();
		}
		return new ArrayList<>(wordsByLetterAndPosition[letter - 'a'][position]);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String>[][] getSets() {
		ArrayList<String>[][] result = new ArrayList[26][WORD_LENGTH];
		for (int letter = 0; letter < 26; letter++) {
			for (int position = 0; position < WORD_LENGTH; position++) {
				result[letter][position] =
						new ArrayList<>(wordsByLetterAndPosition[letter][position]);
			}
		}
		return result;
	}

	public ArrayList<String> getWordsWithDoubleLetter(char letter) {
		letter = Character.toLowerCase(letter);
		if (letter < 'a' || letter > 'z') {
			return new ArrayList<>();
		}
		return new ArrayList<>(wordsWithDoubleLetter[letter - 'a']);
	}

	public ArrayList<String> getAllWordsWithDoubleLetters() {
		ArrayList<String> result = new ArrayList<>();
		for (int letter = 0; letter < 26; letter++) {
			for (String word : wordsWithDoubleLetter[letter]) {
				if (!result.contains(word)) {
					result.add(word);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String fileName = "src/Wordle/WordleSolutionWords.txt";
		if (args.length > 0) {
			fileName = args[0];
		}

		ArrayList<String> words = readWordsFromFile(fileName);
		WordSetSplitter splitter = new WordSetSplitter(words);

		System.out.println("Words read from file: " + words.size());
		System.out.println("Words with 'a' in each position:");
		for (int position = 0; position < WORD_LENGTH; position++) {
			System.out.println("Position " + position + ": "
					+ splitter.getWordsWithLetterAt('a', position));
		}

		System.out.println("Words with 'e' in position 4: "
				+ splitter.getWordsWithLetterAt('e', 4));
		System.out.println("Words with 'r' in position 1: "
				+ splitter.getWordsWithLetterAt('r', 1));
		System.out.println("Words grouped by their repeated letter:");
		for (int letter = 0; letter < 26; letter++) {
			char repeatedLetter = (char) ('a' + letter);
			ArrayList<String> doubleLetterWords =
					splitter.getWordsWithDoubleLetter(repeatedLetter);
			if (!doubleLetterWords.isEmpty()) {
				System.out.println("Double '" + repeatedLetter + "': "
						+ doubleLetterWords);
			}
		}
	}

	private static ArrayList<String> readWordsFromFile(String fileName) throws Exception {
		ArrayList<String> words = new ArrayList<>();
		java.util.Scanner scanner = new java.util.Scanner(new java.io.File(fileName));
		scanner.useDelimiter("\\A");
		String fileText = scanner.hasNext() ? scanner.next() : "";
		scanner.close();

		fileText = fileText.replace("[", "").replace("]", "");
		String[] fileWords = fileText.split(",");
		for (String fileWord : fileWords) {
			String word = fileWord.trim().replace("\"", "");
			if (!word.isEmpty()) {
				words.add(word);
			}
		}
		return words;
	}
}