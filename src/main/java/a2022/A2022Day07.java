package a2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class A2022Day07 extends A2022 {

	public A2022Day07(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2022Day07 d = new A2022Day07(7);
		// System.out.println(d.s1(true));
		long startTime = System.currentTimeMillis();
		//System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public Long s1(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		Dir filesystem = analyerFS(input);
		System.out.println(filesystem);
		return getTotalSizeFileUnder100000(filesystem);

	}

	private Long getTotalSizeFileUnder100000(Dir fs) {
		Long b = 100000L;
		Long res = 0L;
//		System.out.println("/ " + fs.getTotalSize());
		if (fs.getTotalSize() < b) {
			res += fs.getTotalSize();
		}
		for (Dir sd : fs.sousDir) {
			res+= getTotalSizeSubFileUnder100000(sd);
			
		}
		return res;
	}

	private Long getTotalSizeSubFileUnder100000( Dir sd) {
		Long res=0L;
		if (sd.getTotalSize() < 100000L) {
			System.out.println(sd.dirName+" "+sd.getTotalSize());
			res += sd.getTotalSize();
		}
		for (Dir d : sd.sousDir) {
			res += getTotalSizeSubFileUnder100000(d);
		}
		return res;
	}

	private Dir analyerFS(List<String> input) {
		Dir fs = new Dir();
		fs.curPos = "/";
		List<String> instructions = new ArrayList<>();
		int i=0;
		while( i< input.size()) {
			if (input.get(i).length() > 6 && input.get(i).substring(0, 7).equals("$ cd ..")) {
				if(instructions.size()>0) {
					fs = traiterInstructions(fs, instructions);
				}
				fs.applyCDPP();
				i++;
			} else if (input.get(i).substring(0, 4).equals("$ cd")) {
				fs.curPos += input.get(i).substring(5) + "/";
				fs.curPos=fs.curPos.replace("//", "/");
				fs.curPos=fs.curPos.replace("//", "/");
				i++;
			} else if(input.get(i).substring(0, 4).equals("$ ls")){
					instructions = new ArrayList<>();
					int j=1;
					while(i+j<input.size() && !input.get(i+j).substring(0, 1).equals("$")) {
						instructions.add(input.get(i+j));
						j++;
					}
					i=i+j;
					fs = traiterInstructions(fs, instructions);
					instructions = new ArrayList<>();
			} 

		}
		//fs.curPos += instructions.get(0).substring(5) + "/";
		//fs = traiterInstructions(fs, instructions);
		return fs;
	}

	private Dir traiterInstructions(Dir fs, List<String> instructions) {
		
		Dir dir = new Dir(fs.curPos);
		for (int i = 0; i < instructions.size(); i++) {
			if (instructions.get(i).substring(0, 1).equals("d")) {
				String sousDirName = instructions.get(i).substring(4) + "/";
				dir.sousDir.add(new Dir(fs.curPos + sousDirName));
			} else {
				long newFileSize = Long.valueOf(instructions.get(i).split(" ")[0]);
				String newFileName = instructions.get(i).split(" ")[1];
				dir.files.add(new F(newFileName, newFileSize));
			}
		}
		if (fs.curPos.equals("/")) {
			dir.curPos = "/";
			return dir;
		}
		fs = attacher(dir, fs);
		// System.out.println("attaché :");
		System.out.println(fs);
		return fs;
	}

	private Dir attacher(Dir dir, Dir fs) {
		Dir memeNomDansFS = getDirMemeNomDansFS(dir, fs);
		memeNomDansFS.setSousDir(dir.sousDir);
		memeNomDansFS.setFiles(dir.getFiles());
		return fs;
	}

	private Dir getDirMemeNomDansFS(Dir dir, Dir fs) {
		for (Dir d : fs.sousDir) {
			if (d.dirName.equals(dir.dirName)) {
				return d;
			}
			for (Dir dd : d.sousDir) {
				if (dd.dirName.equals(dir.dirName)) {
					return dd;
				}
				for (Dir ddd : dd.sousDir) {
					if (ddd.dirName.equals(dir.dirName)) {
						return ddd;
					}
					for (Dir dddd : ddd.sousDir) {
						if (dddd.dirName.equals(dir.dirName)) {
							return dddd;
						}
						for (Dir ddddd : dddd.sousDir) {
							if (ddddd.dirName.equals(dir.dirName)) {
								return ddddd;
							}
							for (Dir dddddd : ddddd.sousDir) {
								if (dddddd.dirName.equals(dir.dirName)) {
									return dddddd;
								}
								for (Dir ddddddd : dddddd.sousDir) {
									if (ddddddd.dirName.equals(dir.dirName)) {
										return ddddddd;
									}
									for (Dir dddddddd : ddddddd.sousDir) {
										if (dddddddd.dirName.equals(dir.dirName)) {
											return dddddddd;
										}
										for (Dir ddddddddd : dddddddd.sousDir) {
											if (ddddddddd.dirName.equals(dir.dirName)) {
												return ddddddddd;
											}
											for (Dir dddddddddd : ddddddddd.sousDir) {
												if (dddddddddd.dirName.equals(dir.dirName)) {
													return dddddddddd;
												}
												for (Dir ddddddddddd : dddddddddd.sousDir) {
													if (ddddddddddd.dirName.equals(dir.dirName)) {
														return ddddddddddd;
													}
													for (Dir dddddddddddd : ddddddddddd.sousDir) {
														if (dddddddddddd.dirName.equals(dir.dirName)) {
															return dddddddddddd;
														}
														for (Dir ddddddddddddd : dddddddddddd.sousDir) {
															if (ddddddddddddd.dirName.equals(dir.dirName)) {
																return ddddddddddddd;
															}
															for (Dir dddddddddddddd : ddddddddddddd.sousDir) {
																if (dddddddddddddd.dirName.equals(dir.dirName)) {
																	return dddddddddddddd;
																}

															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		}
		return null;
	}

	private class Dir {
		String dirName;
		List<Dir> sousDir;
		List<F> files;
		String curPos = "";

		public void applyCDPP() {
			if (!curPos.equals("/")) {
				System.out.println("curPos avt " + curPos);
				curPos=curPos.substring(0,curPos.length()-1);
				curPos = curPos.substring(0, curPos.lastIndexOf("/")+1 );
				System.out.println("curPos apres " + curPos);
			}
		}

		public Dir getSousDir(String c) {
			for (Dir sd : sousDir) {
				if (sd.dirName.equals(c)) {
					return sd;
				}
			}
			return null;
		}

		public String getCurPos() {
			return curPos;
		}

		public void setCurPos(String curPos) {
			this.curPos = curPos;
		}

		public String getDirName() {
			return dirName;
		}

		public void setDirName(String dirName) {
			this.dirName = dirName;
		}

		public List<Dir> getSousDir() {
			return sousDir;
		}

		public void setSousDir(List<Dir> sousDir) {
			this.sousDir = sousDir;
		}

		public List<F> getFiles() {
			return files;
		}

		public void setFiles(List<F> files) {
			this.files = files;
		}

		public Dir(String dirName, List<Dir> sousDir, List<F> files) {
			super();
			this.dirName = dirName;
			this.sousDir = sousDir;
			this.files = files;
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("- " + dirName);
			s.append("\n");
			for (Dir d : sousDir) {
				s.append("   " + d.toString());
				s.append("\n");
			}
			for (F f : files) {
				s.append(" - " + f.toString());
				s.append("\n");
			}
			return s.toString();
		}

		public Dir() {
			this.dirName = "";
			this.files = new ArrayList<>();
			this.sousDir = new ArrayList<>();
		}

		public Dir(String name) {
			this.dirName = name;
			this.files = new ArrayList<>();
			this.sousDir = new ArrayList<>();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(dirName);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dir other = (Dir) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(dirName, other.dirName);
		}

		private A2022Day07 getEnclosingInstance() {
			return A2022Day07.this;
		}

		public long getTotalSize() {
			return files.stream().mapToLong(e -> e.size).sum() + sousDir.stream().mapToLong(Dir::getTotalSize).sum();
		}

	}

	private class F {
		String name;
		long size;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public F(String name, long size) {
			super();
			this.name = name;
			this.size = size;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(name);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			F other = (F) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(name, other.name);
		}

		@Override
		public String toString() {
			return "" + size + " " + name;
		}

		private A2022Day07 getEnclosingInstance() {
			return A2022Day07.this;
		}

	}

	public Long s2(boolean b) {
		List<String> input = Arrays.asList(getInput(b).split("\n")).stream().map(String::trim)
				.collect(Collectors.toList());
		Dir filesystem = analyerFS(input);
		System.out.println(filesystem);
		return getFolderToDelete(filesystem);
	}

	private Long getFolderToDelete(Dir filesystem) {
		HashMap<String,Long> tailleFolder=getTailles(filesystem);
		Long totalSpace=70000000L;
		Long usedSpace=filesystem.getTotalSize();
		Long unused =totalSpace-usedSpace;
		Long spaceToGetWithDelete=30000000L-unused;
		String res="/";
		Long tailleSupp=10000000000L;
		List<Long> l=new ArrayList<>(tailleFolder.values());
		
		for(String key:tailleFolder.keySet()) {
			if(tailleFolder.get(key)>spaceToGetWithDelete) {
				if(tailleFolder.get(key)<tailleSupp) {
					tailleSupp=tailleFolder.get(key);
					res=key;
				}
			}
		}
		
		l.sort(null);
		System.out.println(l);
		//too high 10618286
		return tailleSupp;
	}

	private HashMap<String, Long> getTailles(Dir filesystem) {
		HashMap<String, Long> tailles=new HashMap<>();
		tailles.put("/",filesystem.getTotalSize());
		for (Dir sd : filesystem.sousDir) {
			tailles= addTaille(tailles,sd);
		}
		return tailles;
	}
	private HashMap<String, Long> addTaille(HashMap<String, Long> tailles, Dir sd) {
		tailles.put(sd.dirName,sd.getTotalSize());
		for(Dir ssd:sd.sousDir) {
			tailles=addTaille(tailles,ssd);
		}
		return tailles;
	}


	public static List<Long> getDuration() {
		A2022Day07 d = new A2022Day07(7);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, endTime - startTime);
	}

}
