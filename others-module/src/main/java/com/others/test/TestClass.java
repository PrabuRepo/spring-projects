
package com.others.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.common.model.ListNode;
import com.common.utilities.Utils;

public class TestClass {
	public static void main(String[] args) {

		TestClass ob = new TestClass();

		byte var1 = -51, var2 = -28;
		System.out.println(var1 & var2);

		int[] A = { 2, 2, 2, 3, 3 };
		int[][] B = { { 1, 3 }, { 5, 4 }, { 2, 4 } };
		//System.out.println(Arrays.toString(getMode(A, B)));

		//test();

		//int[] arr = { 1, 2, 3, 4, 5 };
		//findClosestElements22(arr, 4, 3);

		//isMatch("mississippi", "mis*is*ip*.");

		//ob.cellCompete(new int[] { 1, 1, 1, 0, 1, 1, 1, 1 }, 2).forEach(k -> System.out.print(k + ","));

		//System.out.println(Arrays.toString(ob.zeroSumSubArray(new int[] { 1, -3, 2 })));

		int[] arr = { 3, 2, 8, 5, 1, 4, 6, 0 };
		//ob.dutchFlagAlgSample(arr, 4);
		//System.out.println(Arrays.toString(arr));

		String str = "Bob hit a ball, the hit BALL flew far after it was hit.";
		String[] words = str.toLowerCase().split("\\W+");
		//System.out.println(Arrays.toString(words));

		String words1[] = { "Tushar", "likes", "to", "write", "code", "at", "free", "time" };
		//System.out.println(ob.justify1(words1, 12));

		//System.out.println(ob.justify2(words1, 12));

		ListNode head = new ListNode(2);
		head.next = new ListNode(1);
		head.next.next = new ListNode(5);

		//System.out.println(ob.nextLargerNodes(head));

		ob.sortMap();
	}

	public void sortMap() {
		Map<String, Integer> map = getUnSortedMap();

		//Sort based on key
		System.out.println("Sort map based on key ->");
		TreeMap<String, Integer> treeMap = new TreeMap<>(map);
		treeMap.forEach((k, v) -> System.out.println(k + " - " + v));

		System.out.println("\nSort map based on value using list ->");
		//Sort based on value using list:
		List<Entry<String, Integer>> entryList = new ArrayList<>();
		//Add all the entryset into list
		entryList.addAll(map.entrySet());
		Collections.sort(entryList, (a, b) -> a.getValue().equals(b.getValue()) ? a.getKey().compareTo(b.getKey())
				: a.getValue() - b.getValue());

		LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
		for (Entry<String, Integer> entry : entryList) {
			linkedHashMap.put(entry.getKey(), entry.getValue());
		}
		linkedHashMap.forEach((k, v) -> System.out.println(k + " - " + v));

		System.out.println("\nSort map based on value using priority queue ->");
		//Sort based on value using list:
		PriorityQueue<Entry<String, Integer>> queue = new PriorityQueue<>(
				(a, b) -> a.getValue().equals(b.getValue()) ? a.getKey().compareTo(b.getKey())
						: b.getValue() - a.getValue());
		//Add all the entryset into priority queue
		queue.addAll(map.entrySet());

		LinkedHashMap<String, Integer> linkedHashMap2 = new LinkedHashMap<>();
		while (!queue.isEmpty()) {
			Entry<String, Integer> entry = queue.poll();
			linkedHashMap2.put(entry.getKey(), entry.getValue());
		}
		linkedHashMap2.forEach((k, v) -> System.out.println(k + " - " + v));
	}

	public void sortByValueJava8Stream() {
		Map<String, Integer> unSortedMap = getUnSortedMap();

		System.out.println("Unsorted Map : " + unSortedMap);

		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

		System.out.println("Sorted Map   : " + sortedMap);

		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

		System.out.println("Reverse Sorted Map   : " + reverseSortedMap);
	}

	private Map<String, Integer> getUnSortedMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put("com", 1345);
		map.put("google.com", 900);
		map.put("stackoverflow.com", 10);
		map.put("yahoo.com", 410);
		map.put("mail.yahoo.com", 60);
		map.put("mobile.sports.yahoo.com", 10);
		map.put("sports.yahoo.com", 50);
		map.put("org", 3);
		map.put("wikipedia.org", 3);
		map.put("mobile.sports", 1);
		map.put("sports", 1);
		return map;
	}

	public int[] nextLargerNodes(ListNode head) {
		//0=>index, 1=>val
		Stack<int[]> stack = new Stack<>();
		int index = 0;
		List<Integer> result = new ArrayList<>();

		while (head != null) {
			while (!stack.isEmpty() && head.data > stack.peek()[1]) {
				int[] curr = stack.pop();
				System.out.println(Arrays.toString(curr));
				result.add(curr[0], curr[1]);
				System.out.println(Arrays.toString(curr));
			}
			stack.push(new int[] { index, head.data });
			index++;
			head = head.next;
		}

		while (!stack.isEmpty()) {
			int[] curr = stack.pop();
			result.set(curr[0], curr[1]);
		}

		return result.stream().mapToInt(i -> i.intValue()).toArray();
	}

	//Using Greedy Algorithm:
	public String justify1(String words[], int width) {
		StringBuilder result = new StringBuilder();

		int i = 0, j = 0, n = words.length;
		while (i < n) {
			int len = words[i].length();
			result.append(words[i]);
			j = i + 1;
			while (j < n && len + words[j].length() + (j - i - 1) < width) {
				result.append(" ").append(words[j]);
				len += words[j].length();
				j++;
			}
			if (j != n) result.append(" ");
			result.append("\n");
			i = j;
		}

		return result.toString();
	}

	public String justify2(String words[], int width) {
		int cost[][] = new int[words.length][words.length];

		//next 2 for loop is used to calculate cost of putting words from i to j in one line. If words don't fit in one line then we put
		//Integer.MAX_VALUE there.
		for (int i = 0; i < words.length; i++) {
			cost[i][i] = width - words[i].length();
			for (int j = i + 1; j < words.length; j++) {
				cost[i][j] = cost[i][j - 1] - words[j].length() - 1;
			}
		}

		for (int i = 0; i < words.length; i++) {
			for (int j = i; j < words.length; j++) {
				cost[i][j] = cost[i][j] < 0 ? Integer.MAX_VALUE : (int) Math.pow(cost[i][j], 2);
			}
		}

		//minCost from i to len is found by trying j between i to len and checking which one has min value
		int minCost[] = new int[words.length];
		int result[] = new int[words.length];
		for (int i = words.length - 1; i >= 0; i--) {
			minCost[i] = cost[i][words.length - 1];
			result[i] = words.length;
			for (int j = words.length - 1; j > i; j--) {
				if (cost[i][j - 1] == Integer.MAX_VALUE) continue;

				if (minCost[i] > minCost[j] + cost[i][j - 1]) {
					minCost[i] = minCost[j] + cost[i][j - 1];
					result[i] = j;
				}
			}
		}
		System.out.println("Minimum cost is " + minCost[0]);
		return getResult(words, result);
	}

	private String getResult(String[] words, int[] result) {
		int i = 0;
		int j;
		//finally put all words with new line added in string buffer and print it.
		StringBuilder builder = new StringBuilder();
		do {
			j = result[i];
			for (int k = i; k < j; k++) {
				builder.append(words[k] + " ");
			}
			builder.append("\n");
			i = j;
		} while (j < words.length);
		return builder.toString();
	}

	public int[][] updateMatrix(int[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;

		Queue<int[]> queue = new LinkedList<>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 0) {
					queue.offer(new int[] { i, j });
				} else {
					matrix[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			int r = cell[0];
			int c = cell[1];
			for (int[] d : dirs) {
				int i = r + d[0];
				int j = c + d[1];
				if (i < 0 || i >= m || j < 0 || j >= n || matrix[i][j] <= matrix[r][c] + 1) continue;

				queue.add(new int[] { i, i });
				matrix[i][j] = matrix[r][c] + 1;
			}
		}

		return matrix;
	}

	public int[] dutchFlagAlgSample(int[] arr, int pivot) {
		int l = 0, h = arr.length - 1, curr = 0;
		while (curr <= h) {
			if (arr[curr] < pivot) {
				Utils.swap(arr, l, curr);
				l++;
				curr++;
			} else if (arr[curr] == pivot) {
				curr++;
			} else if (arr[curr] > pivot) {
				Utils.swap(arr, curr, h);
				h--;
			}
		}
		return arr;
	}

	public int[] zeroSumSubArray(int[] arr) {
		//Hashmap: Key: sum[0,i - 1]; Val: index
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if (map.containsKey(sum)) {
				int oldIndex = map.get(sum);
				// Sum range from next index of sum to curr index
				return Arrays.copyOfRange(arr, oldIndex + 1, i + 1);
			} else {
				map.put(sum, i);
			}
		}
		return null;
	}

	public List<Integer> cellCompete(int[] states, int days) {
		int n = states.length;
		while (days-- > 0) {
			int[] temp = new int[n];
			for (int i = 0; i < n; i++) {
				if (i == 0) {
					temp[i] = states[i + 1];
				} else if (i == n - 1) {
					temp[i] = states[i - 1];
				} else {
					temp[i] = states[i - 1] ^ states[i + 1];
				}
			}
			//System.out.println(Arrays.toString(temp));
			states = temp;
		}

		return Arrays.stream(states).boxed().collect(Collectors.toList());
	}

	public static boolean isMatch(String s, String p) {
		int m = s.length(), n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		// Note: Here i-1, j-1 is curr index, i-2, j-2 is prev index
		// Base case: For both s & p are empty
		dp[0][0] = true;

		// To handle the first row or empty string with whole pattern 'p'
		for (int j = 2; j <= n; j++)
			if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 2];

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.') {
					dp[i][j] = dp[i - 1][j - 1];
				} else if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i][j - 2]; //This is for 0 occurrence of the prev char
					if (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.')
						dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
				} else {
					dp[i][j] = false;
				}
			}
		}

		System.out.println(Arrays.deepToString(dp));

		return dp[m][n];

	}

	public static List<Integer> findClosestElements22(int[] arr, int k, int x) {
		// maxHeap, sort descendingly according to diff to x
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
				(a, b) -> Math.abs(x - b) == Math.abs(x - a) ? b - a : Math.abs(x - b) - Math.abs(x - a));
		// each time, if we have better option, delete num with max diff from x, and insert the new num
		for (int num : arr) {
			maxHeap.offer(num);
			if (maxHeap.size() >= k) {
				maxHeap.poll();
			}
		}
		// convert heap back to List<Integer> and sort them to get the original order
		List<Integer> res = new ArrayList(maxHeap);
		res.forEach(i -> System.out.println(i));
		Collections.sort(res);
		return res;
	}

	public static int[] getMode(int[] A, int[][] B) {
		if (B.length == 0) return new int[] {};
		int n = B.length;
		int[] result = new int[n];
		PriorityQueue<Node> queue = new PriorityQueue<>(
				(a, b) -> a.count != b.count ? a.count - b.count : a.val - b.val);
		Map<Integer, Node> map = new HashMap<>();

		for (int val : A) {
			if (map.get(val) == null) map.put(val, new Node(val, 0));
			map.get(val).count += 1;
		}

		for (Node node : map.values()) {
			queue.add(node);
		}

		int index = 0;
		for (int[] arr : B) {
			int i = arr[0] - 1, newVal = arr[1];
			int oldVal = A[i];
			Node node = map.get(oldVal);
			queue.remove(node);

			if (node.count == 1) {
				map.remove(oldVal);
			} else {
				node.count--;
				map.put(oldVal, node);
				queue.add(node);
			}

			//update the new value:
			A[i] = newVal;
			Node newNode = map.get(newVal);
			if (newNode == null) {
				newNode = new Node(newVal, 0);
				map.put(newVal, newNode);
			} else {
				queue.remove(newNode);
			}
			newNode.count += 1;
			queue.add(newNode);

			result[index++] = queue.peek().val;
		}

		return result;
	}

	public static void test() {

		//	list.add("");
		TreeSet<Integer> set = new TreeSet<>();
		set.add(3);
		set.add(2);
		set.add(Integer.parseInt("2"));
		set.add(new Integer(2));

		set.forEach(k -> System.out.println(k));
	}

	public static int add(int a, int b) {
		return a + b;
	}

	public static boolean isBalanced(String str) {
		if (str == null || str.length() == 0) return true;
		Stack<Character> st = new Stack<>();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!st.isEmpty() && ((st.peek() == '{' && ch == '}') || (st.peek() == '(' && ch == ')'))) {
				st.pop();
			} else {
				st.push(ch);
			}
		}

		return st.isEmpty() ? true : false;
	}

	public static String decryptPassword(String s) {
		if (s == null || s.length() <= 1) return s;
		int n = s.length();
		StringBuilder sb = new StringBuilder(s);
		//Replace 0 with numbers
		int l = 0, h = n - 1;

		while (l < h) {
			if (!Character.isDigit(sb.charAt(l))) break;
			while (l <= h && sb.charAt(h) != '0') h--;
			if (l >= h) break;
			sb.setCharAt(h, sb.charAt(l));
			sb.deleteCharAt(l);
		}
		//System.out.println("1: "+sb.toString());
		//Swap if there is an '*'
		for (int i = 0; i < sb.length(); i++) {
			if (i > 1 && sb.charAt(i) == '*') {
				char temp = sb.charAt(i - 2);
				sb.setCharAt(i - 2, sb.charAt(i - 1));
				sb.setCharAt(i - 1, temp);
				sb.deleteCharAt(i);
			}
		}
		//System.out.println("2: "+sb.toString());
		return sb.toString();
	}

	private static String input() {
		return "671212498833394335636258411111761191951868786253285416839761558381899954115464527653212129364852895631867124237285961224537222752128248113775477646191529979175899662921726944178363859224341282267979852113776272639679625287438427369156817698966157152532146448324235111898873365196233973675968126635183233168415742341477643114772871361379271719996686627589136116225985898255867481311798431764164726195443649572783985921467352446989737128337873252995833572434528835264276211424173775433511995724143528999128177977216851341739832554596456827174146448512478721331223134988265339854533549934651885914398139256125347519173491491557828282327722225154237672637187766171512545847919876598723114679115849474738446575867487998163967136873658674747811916451596883256714959963695731444273488765399264852425187969798739283275635897491891813357293726445751742969731222819586172984752979941292239292871716441691956341587796472297684332886335995294884472833751176497631574336495622989255285598394261439792588612719286411812314474763554731938695129551117683149461359885377254631822782555193348744975398953623139542821553291227125655694336411547875218122358275185984795635726237115387458887666758975479776434251274686458496133293649777574757855623241465976555773794866888396258753565215713334547666762913917341988722467826319318441569374179861145213431941847666621762127479173644199145365154973244268862178527167388187628835316484468344763969133355289827713249893494921923474653119442266254188154594173295241676459534572418674635178762562364797573499771914757113662764543658332886744217752751743885264823184343936468732341864433149947561253229181479276584555734191645267779628421573592945921933233796781661939875899911261938922171412814726245884542511152177724441951296925134632731514856139648126131764576138615497743562477333521245574211116895454851585639916879984744573523933828474954174774955146236961737388249285565115571574647451726457896987356563179625547239175512434588379638736731365299635371912966998924875246753857779764195222841475831217271896752912913622256954638541353366152768163356785279196689938476613752988437793347829996292686636637126778446771389558555172163318951193968527474729576748554178155346848449766233797812865148656257436155931159931378679911242993996895817614432663138968789613275897113468748762364834129148748449775212166842885788855668366573667773766573676795852484135932496485447137873973815338993295833735949687737728712295828649517981776372222536593668241956285233136266562451485324149745267844956333568379794791475796347736966981716841998316193532825954386592331824455485618785475876677492266818846387484997875997761699875767915449615529289367759341235582655233581776818244345536896681562491816899249882869572443971948366135898658711113151529581196725161672484944913834232778443972312319464281353779567644847171937854637315349318343318427544954667381779721939558722995474148651376541725174699882199156951439318235541797984815264755227714165577945241897579697589716431554327941726887647595166827351546546542666714569193145117691914347655726472487889379668574312538217454943418487131826144132421968313979329222662179313589498166528217914792715372913563372513649129279491487622819932738331289418313242264415982746593933665825924468116543722155626529696354518868897474834127548121587292316176287955695838346471927648716863492111244283989643251825771441967812638364397528966331661543627567851526277211314742945976913177216715314461823538195398451247653312174888585415912529873558414238983777959291532729937997577715366871237829416231139851162914897327344814133571742265494978745535292825121149857173356384637378688868983324355463642364589425878284343244797573372862825995373543681441468418173118114982255231951773999845939472341796762576888483245551653744524685192327229527692675133219857136741635334771687393829171829698945214489151959298265314118896868491338358968221986556847213458971774192658528618294941884529259249313319857649967218953626854557862381581539322494354914817293751361398196477317419988655776763968779365538124759223774615266852292741486417841564698961833432864255423918318264439199535548165944645783621139278698661116327354185331941774249414342333642816165182487328465478746978715987791186885785195256322389922949495917881113995785859459765313161521413154144884937225137595537434342834547935277433851728116492637232726811836543474889982455933625879432819515664786937382645153452841652991924722244361948284764119878225767699238926682112756584567752562318255242299193655522531731877393412725634852658619457517184618967383929924413677677721959955864313698788123526458353516961837699956738248413365989248476967987531355114243937126597575825685217429677333239588879843637763973573298315762227623523325972553985516321712266365413759733233632394566632799753264597977725314871551895435419543944739368753694568873659851174179752843723328419121777142615824622631668973413122713166179734368973797487127112675737392334321357947645833235518863776631545755973175144977417158464738722982669681751441276713969853544753974862761467924384459999714957286523432314811717473416558811745559926248419566261271375321168522512911456276681389369277999375944341172476512117429248917631323551584375722988746343264431277979632652265832649537654617956583317247225376919497137747285328887935944813325842175191954874321937762539111669793856422651952353847576651425698541778527983648994647115134127831845358719532232979288166598786732183785265862183646139554126492672988249637231123785126547675151574937123971991787138274584573663981327876641619535333365733321171989695351845952476829476991573332829675895992442727257721862415342676467567696347858442251334611749929871734286695621758715286883469623951841822244551437443525753754927494462472366856174849519791478131529586276782487827124656973656523268849511228712865315788266953169127356893559234284739976847241757948546781952562456599248679465699444134515127725761812275355398335251947613164665815927314651969168527761255359986135799117532665865889476983827272882861939871843547941399427197155666375172984694835727579333957927448956661375423891337677543163199265767226176863129169186778686922339438352212529748623275399647934639634281961117348184193976556366641141595726694541719763396939176313925218649295393887238816356411296952716521964749263157464486597135675624259579188328244139291151895279713766718149857487819651385422923427823436155224958728261432363159786894384678981165187658445443618524168293487488185837397518978355988213166115133377427768413971996171733316382139824361425925628853377215476964623392183317235597726797635922534345199993132531297812763376753743944342571315162175468569934178557755522553178269914822698866821171625865485558743398544467995838464616626938248469433694657571629147567744957266247519128914226446984199819192312874874813195248276267436441754677244496893663954793988797463723832198189388978733666688722725861163467528893341229649398645438893698475771721373447672458832675789564626316394388658561159146921562691942861376486922135767554834941947521296418551793267337644825778751833126476393293924575119366741348394466458131893633182852786724616771949394226768256288736918913811815234641162919351493385579198227598459518355948996387665697715113621363932725797219724533525369222677532914138437319327156815158797389956688234427843918173288165677915245156878484844289666616176998662321223134313191848672931282814285126292234994269627852674125269482375787275915132961617229564496132962567168214596848167526539768835416296241662259346495831738838563531547891684995623267657263245271739998144762922143893952868445162786255557381165737656437668349475645939666939694236456745561849653496796755216139851326751166543575611119471886616728266116398525479338392572666263456864944465845167195359918372978915237976991882187798382748659915284241594717245745441197656538767668928779843262797547863467279499119816313852941477716656388225612948127178483396866185782969469748561486275192923682313641233656696646318789327954283869754665128852465756554284799857612597764618497333464951992378513922546414255432724441239852716174846928334964455436436793331582987875449635885515975613727788362367692418943457279371351164177469884512743255569548619421918999989897563177453651656393149896726632837646862618926431563559155627942142939414761581425934324954797632821391722772382735555953118573546311288392936365333818383571874819513647231394397596491446571415439939239961821612647871914178195241469685494422324616977613437773892139373837621642896965684446447962454823719255948971845737186653299127175838394757823271911357699885954836172298997696682859512941117937184317893342865349245323764678252676713263676573778648126936142912846481891282614787661812924643772293214247362625447516548964474324487344996889561194439425498575195696851826589931648831289668397661925827677891397491661853331168272784968859349467427132913178827536576688395592625587262731672893359126649938838557623285919915362499989185225425419949349742827241433341642717634926233821621569759881497789373659577987974888251721946879216969694725374881495362475496912499754389559868393753931311859175333935279446411728955912729314761822825962158421778539756615536154486823241716944641652694376859187584914159793165462187628499799191999378781955998292649377987622345157199932863547693132363526912584646682335465462278935451314559928761977212965785893942326965759784981632812741375459564129192646259278173677533261748553154532612981934792911729741495884879991346519917366387912916899381815758821763768549995267538249256478851643473347661948961912361159772554779995724831542681552842941468694198148185866515742654922543376199135974637568293424131137495342172521755997859844425851811173813158728682928423814596859591219426383258827668462447869663491715225449315293179453226447579971288141648464726149396287178756343547131751274461925198975156975713763559373386349562157629363521748342626961313663867143757293926466686722675339339872747479448713541734224236157338956134639811461374855732243617676534962676192933827151336358966292166479124252996224297995576784249254246835147557853641552594976747395157381715378699455484187662763869222411231272533831753655447862325749837988969233642382279225556844242221817425793967286219417351";
	}

}

class Node {
	public int val;
	public int count;

	public Node(int val, int cnt) {
		this.val = val;
		this.count = cnt;
	}
}
