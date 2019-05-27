package com.yd.java.jdk.aio.client;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console implements Runnable {
	protected static final Pattern IGNORE = Pattern.compile(".*");
	protected static final Pattern WORD = Pattern.compile("\\S+");
	protected List<CommandProvider> listeners = new ArrayList<CommandProvider>();
	protected Scanner scanner;

	public Console() {
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
		String string;
		System.out.println("Type ? for help, exit for quit. \nwaiting commands...");
		while (true) {
			string = scanner.next();
			if (string.equalsIgnoreCase("exit"))
				break;
			if (string.equalsIgnoreCase("?")) {
				help();
				continue;
			}
			checkCommand(string);
		}
		scanner.close();
	}

	protected void help() {
		for (CommandProvider iterator : listeners) {
			System.out.print(iterator.getClass().getSimpleName());
			System.out.print(": ");
			System.out.println(Arrays.toString(iterator.commands()));
		}
	}

	protected void checkCommand(String string) {
		CommandName command;
		CommandProvider listener;
		outer: {
			for (CommandProvider iterator : listeners) {
				for (CommandName commandName : iterator.commands()) {
					if (commandName.name().equalsIgnoreCase(string)) {
						command = commandName;
						listener = iterator;
						break outer;
					}
				}
			}
			skipLine();
			return;
		}
		int count = command.parameterCount();
		if (count < -2) {
			skipLine();
			System.out.println("Invalid command count of parameter " + count);
			return;
		}
		switch (count) {
		case 1:
			string = scanner.next();
			skipLine();
			listener.handle(command, string);
			break;
		case 0:
			skipLine();
			listener.handle(command);
			break;
		case -1:
			// x*
			string = scanner.findInLine(WORD);
			if (string == null) {
				listener.handle(command);
			} else {
				skipLine();
				listener.handle(command, string);
			}
			break;
		case -2:
			// x+
			string = scanner.next();
			List<String> list = new ArrayList<String>(2);
			list.add(string);
			while (true) {
				string = scanner.findInLine(WORD);
				if (string == null)
					break;
				list.add(string);
			}
			int size = list.size();
			if (size == 1)
				listener.handle(command, list.get(0));
			else
				listener.handle(command, list.toArray(new String[size]));
			break;
		case 2:
		default:
			String[] parameters = new String[count];
			for (int i = 1; i <= count; i++)
				parameters[i] = scanner.next();
			skipLine();
			listener.handle(command, parameters);
		}
	}

	protected void skipLine() {
		scanner.skip(IGNORE);
	}

	public Iterator<CommandProvider> iterator() {
		return listeners.iterator();
	}

	public boolean add(CommandProvider e) {
		return listeners.add(e);
	}

	public void add(int index, CommandProvider element) {
		listeners.add(index, element);
	}

}
