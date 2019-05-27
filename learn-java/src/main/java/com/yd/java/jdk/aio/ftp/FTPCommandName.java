package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.client.CommandName;


public enum FTPCommandName implements CommandName {
	/**
	 * USER <SP> <username> <CRLF>
	 */
	USER(1),
	/**
	 * PASS <SP> <password> <CRLF>
	 */
	PASS(1),
	/**
	 * ACCT <SP> <account-information> <CRLF>
	 */
	ACCT(1),
	/**
	 * CWD <SP> <pathname> <CRLF>
	 */
	CWD(1),
	/**
	 * CDUP <CRLF>
	 */
	CDUP(0),
	/**
	 * SMNT <SP> <pathname> <CRLF>
	 */
	SMNT(1),
	/**
	 * QUIT <CRLF>
	 */
	QUIT(0),
	/**
	 * 
	 */
	EPRT(0), FEAT(0), MDTM(0), MKD(1), OPTS(0), XCUP(0), XCWD(1), XMKD(1), XPWD(0), XRMD(1),
	/**
	 * REIN <CRLF>
	 */

	REIN(1),
	/**
	 * SIZE <SP><filename> <CRLF>
	 */
	/**
	 * PORT <SP> <host-port> <CRLF>
	 */
	PORT(1),
	/**
	 * PASV <CRLF>
	 */
	PASV(0), EPSV(0),
	/**
	 * TYPE <SP> <type-code> <CRLF>
	 */
	TYPE(1),
	/**
	 * STRU <SP> <structure-code> <CRLF>
	 */
	STRU(1),
	/**
	 * MODE <SP> <mode-code> <CRLF>
	 */
	MODE(1),
	/**
	 * SIZE <SP> <pathname> <CRLF>
	 */
	SIZE(1),
	/**
	 * RETR <SP> <pathname> <CRLF>
	 */
	RETR(1),
	/**
	 * STOR <SP> <pathname> <CRLF>
	 */
	STOR(1),
	/**
	 * STOU <CRLF>
	 */
	STOU(0),
	/**
	 * APPE <SP> <pathname> <CRLF>
	 */
	APPE(1),
	/**
	 * ALLO <SP> <decimal-integer> [<SP> R <SP> <decimal-integer>] <CRLF>
	 */
	ALLO(-1),
	/**
	 * REST <SP> <marker> <CRLF>
	 */
	REST(1),
	/**
	 * RNFR <SP> <pathname> <CRLF>
	 */
	RNFR(1),
	/**
	 * RNTO <SP> <pathname> <CRLF>
	 */
	RNTO(1),
	/**
	 * ABOR <CRLF>
	 */
	ABOR(0),
	/**
	 * DELE <SP> <pathname> <CRLF>
	 */
	DELE(1),
	/**
	 * RMD <SP> <pathname> <CRLF>
	 */
	RMD(1),
	/**
	 * MKD <SP> <pathname> <CRLF>
	 */
	MDK(1),
	/**
	 * PWD <CRLF>
	 */
	PWD(0),
	/**
	 * LIST [<SP> <pathname>] <CRLF>
	 */
	LIST(-2),
	/**
	 * NLST [<SP> <pathname>] <CRLF>
	 */
	NLST(-2),
	/**
	 * SITE <SP> <string> <CRLF>
	 */
	SITE(1),
	/**
	 * SYST <CRLF>
	 */
	SYST(0),
	/**
	 * STAT [<SP> < pathname >] <CRLF >
	 */
	STAT(-2),
	/**
	 * HELP [ < SP > < string > ] < CRLF >
	 */
	HELP(-2),
	/**
	 * NOOP < CRLF >
	 */
	NOOP(0);
	/**
	 * <ul>
	 * FTP command parameter count.
	 * <li>positive number or zero, indicate how much parameters.
	 * <li>-1 indicate at least one, just like "+"
	 * <li>-2 indicate maybe one, just like "?"
	 * </ul>
	 */
	public int parameterCount;

	private FTPCommandName(int parameterCount) {
		this.parameterCount = parameterCount;
	}

	@Override
	public int parameterCount() {
		return parameterCount;
	}
}
