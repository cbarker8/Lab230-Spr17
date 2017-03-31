import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Set;

class ClientHandler extends Thread {

	BufferedReader brIn;
	PrintWriter pwOut;
	Server server;
	Socket socket;
	String currentUserName;
	String ipAddress;

	ClientHandler(Socket sock, Server serv) {
		try {
			brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pwOut = new PrintWriter(sock.getOutputStream(), true);
			server = serv;
			socket = sock;
			ipAddress = sock.getRemoteSocketAddress().toString().substring(1);
		} catch (Exception e) {
			System.out.println("Error initiating ClientHandler");
		}
	}

	public void run() {
		String line = new String();
		try {
			while (true) {
				line = brIn.readLine();
				String[] data = line.split(",");
				System.out.println(line);
				switch (data[0]) {
				case "<login>":
					loginUser(data);
					break;
				case "<logout>":
					server.removeUserFromOnline(currentUserName);
					server.logToGUI(currentUserName + " logged out.");
					break;
				case "<addElection>":
					server.addElection(data[1], data[2]);
					createElectionList("<getElections>,");
					consoleGUI.updateCurrentElections(server.elections.keySet());
					server.logToGUI(currentUserName + " has added election " + data[1]+".");
					pwOut.println("<AddedElection>," + data[1] + "," + data[2]);
					break;
				case "<getElections>":
					createElectionList("<getElections>,");
					break;
				case "<initElections>":
					createElectionList("<initElections>,");
					break;

				case "<removeElection>":
					if (server.removeElection(data[1])) {
						pwOut.println("<removedElection>," + data[1]);
						server.logToGUI(currentUserName + " has removed election " + data[1]+".");
					} else {
						pwOut.println("<removedElection>," + data[1] + ",fail");
					}
					consoleGUI.updateCurrentElections(server.elections.keySet());
					break;
				case "<addRace>":
					server.addRace(data[1], data[2]);
					pwOut.println("<addedRace>," + data[1] + "," + data[2]);
					server.logToGUI(currentUserName + " has added race " + data[2] + " to " + data[1]+".");
					break;
				case "<removeRace>":
					server.elections.get(data[1]).removeRace(data[2]);
					pwOut.println("<removedRace>," + data[1] + "," + data[2]);
					server.logToGUI(currentUserName + " has removed race " + data[2] + " from " + data[1]+".");
					break;
				case "<getRaces>":
					Set<String> raceNames = server.elections.get(data[1]).getAllRaces();
					StringBuilder races = new StringBuilder("<getRaces>,");
					for (String r : raceNames) {
						races.append((r + ","));
					}
					pwOut.println(races.toString());
					break;
				case "<addCand>":
					server.elections.get(data[1]).getRace(data[2]).addCandidate(data[3]);
					pwOut.println("<addedCand>," + data[1] + "," + data[2] + "," + data[3]);
					server.logToGUI(currentUserName + " has added candidate " + data[3] + " to race " + data[2]+ " in election "+ data[1]+".");

					break;
				case "<removeCand>":
					server.elections.get(data[1]).getRace(data[2]).disqualify(data[3]);
					pwOut.println("<removedCand>," + data[1] + "," + data[2] + "," + data[3]);
					server.logToGUI(currentUserName + " has removed candidate " + data[3] + " from race " + data[2]+ " in election "+ data[1]+".");
					break;

				case "<getCands>":
					Set<String> candNames = server.elections.get(data[1]).getRace(data[2]).getCandidates();
					StringBuilder cands = new StringBuilder("<pushCands>,");
					for (String r : candNames) {
						cands.append((r + ","));
					}
					pwOut.println(cands.toString());
					break;
				case "<getRandCands>":
					ArrayList<String> randCandNames = server.elections.get(data[1]).getRace(data[2]).getRandomCandidates();
					StringBuilder randCands = new StringBuilder("<pushCands>,");

					for (String r : randCandNames) {
						randCands.append((r + ","));
					}
					pwOut.println(randCands.toString());
					break;
				case "<getElectionStructure>":
					Election currentE = server.elections.get(data[1]);
					StringBuilder structure = new StringBuilder("<electStructure>," + currentE.getAllRaces().size() + ",");
					for (String raceName : currentE.getAllRaces()) {
						structure.append(raceName + "," + currentE.getVoteCount().get(raceName).keySet().size() + ",");
						ArrayList<String> randName = server.elections.get(data[1]).getRace(raceName).getRandomCandidates();
						for (String candName : randName){
							structure.append(candName+ ",");
						}
					}
					pwOut.println(structure.toString());
					break;
				case "<vote>":
					// Add a server.election check for if current user has voted
					// id = data[1]
					server.elections.get(data[2]).getRace(data[3]).vote(data[4]);
					// pwOut.println("<voteReceived>,Fail");
					break;
				case "<voteDone>":
					pwOut.println("<voteReceived>,Success");
					break;
				case "<getVoteCount>":
					Election currentElection = server.elections.get(data[1]);
					StringBuilder votes = new StringBuilder("<voteCounts>," + currentElection.getAllRaces().size() + ",");
					for (String raceName : currentElection.getAllRaces()) {
						votes.append(raceName + "," + currentElection.getVoteCount().get(raceName).keySet().size() + ",");
						for (String candName : currentElection.getVoteCount().get(raceName).keySet())
							votes.append(candName + "," + currentElection.getVoteCount().get(raceName).get(candName) + ",");
					}
					pwOut.println(votes.toString());
					server.logToGUI(currentUserName + " has requested vote count in election "+ data[1] + ".");
					break;
				case "<backup>":
					server.logToGUI(currentUserName + " has requested a backup.");
					for(String electName : server.elections.keySet()){
						server.backup(electName);
					}
					break;
				case "<clientQuit>":
					server.removeClientHandler(this, ipAddress);
					break;
				case "<die>":
					die();
				default:
					pwOut.println("<error>");
				}
			}
		} catch (SocketException x) {
			System.out.println("socket disconnected - User x'd out");
			try {
				server.removeUserFromOnline(currentUserName);
				server.removeClientHandler(this, ipAddress);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			server.logToGUI("ERROR : " + e.getMessage() + "SERVER DYING");
			server.die();
			die();
		}
	}

	private void loginUser(String[] data) {
		if (data.length == 3) {
			if (server.onlineUsers.contains(data[1])) {
				pwOut.println("<logged>,YiJing");
				return;
			} else if (server.loginUser(data[1], data[2])) {
				createElectionList("<initElections>,");
				String userType = server.getUserType(data[1]);
				String userID = server.getUserID(data[1]);
				currentUserName = data[1];
				server.logToGUI("[" + server.getUserType(data[1]) + "][" +server.getUserID(data[1])+"]"+ data[1] + " has logged in.");
				pwOut.println("<logged>," + userType + "," + data[1] + "," + userID);
				return;
			}
		}
		pwOut.println("<logged>,FAIL");
	}

	private void createElectionList(String start) {
		Set<String> keys = server.elections.keySet();
		StringBuilder elections = new StringBuilder(start);
		for (String k : keys) {
			elections.append((k + ","));
		}
		pwOut.println(elections.toString());
	}

	public void sendmsg(String msg) {
		pwOut.println(("<serverBROADCAST>," + msg));
	}

	private void die() {
		try {
			socket.close();
			server.die();
		} catch (Exception e) {
			// shutting down
		}
		System.exit(0);
	}
}
