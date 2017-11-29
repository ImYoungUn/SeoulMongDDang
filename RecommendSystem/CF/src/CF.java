import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CF {
	// ���� ������ �ȵ���̵�� ���� ����!
	/*
	void test() throws IOException {
		int num = 5;
		int a = 3;
		float[] simPerson = new float[5];
		ArrayList<Person> person = new ArrayList();

		BufferedReader[] br = new BufferedReader[5];
		String[] line = new String[5];
		for (int i = 0; i < 5; i++) {
			person.add(new Person());
			br[i] = new BufferedReader((new InputStreamReader(System.in)));
			line[i] = br[i].readLine();
			line[i] = line[i].replaceAll(System.getProperty("line.separator"), " ");
			String[] spl = line[i].split(" ");
			for (int j = 0; j < 15; j++) {
				// System.out.println(spl.length + " " + j + " : " + spl[j]);
				System.out.print(Integer.parseInt(spl[j]));
				person.get(i).rate.add(Integer.parseInt(spl[j]));
				person.get(i).cultId.add(j);
				
			}
			System.out.println();
		}

		EQ1 eq1 = new EQ1();
		EQ2 eq2 = new EQ2();
		for (int i = 0; i < num; i++) {
			if (a != i )
				simPerson[i] = eq1.getSim(a,i,person);
		}
		System.out.println(
				simPerson[0] + ", " + simPerson[1] + ", " + simPerson[2] + ", " + simPerson[3] + ", " + simPerson[4]);
		float expectScore;
		expectScore = eq2.expect(11, 3, simPerson, person);
		System.out.println("11��° ���� ���� = " + expectScore);
		// System.out.println(eq.getSim(a, a));
		// System.out.println(eq.getSim(a, b));
		// System.out.println(eq.getSim(a, d));
		// System.out.println(eq.getSim(a, e));
	}
*/
}
/*
 * 
0 0 0 3 5 3 3 3 2 1 1 5 5 5 0

0 0 0 2 5 3 3 3 1 1 1 5 5 5 0

0 0 0 -1 -5 3 3 1 1 1 1 -5 5 5 0

0 0 0 3 5 3 3 3 2 1 0 5 5 1 0

0 0 0 3 5 3 3 2 1 1 1 5 4 4 0

 */



class EQ1 {
	float getSim(int a, int u, ArrayList<Person> p) {
		// using Equation 1. ����� a�� u�� ���絵
		float sim;
		float sum1 = 0, sum2 = 0, sum3 = 0;
		float aAvg = 0, uAvg = 0;
		int same[] = new int[4];
		ArrayList<Integer> aArr = new ArrayList();
		ArrayList<Integer> uArr = new ArrayList();

		// cultId�� ���� �͵��� ã�Ƽ� �� Arr�� ����
		//a�� u �� userid �ε�, userid�� ������� �� �ִ� ���� �ƴ϶�
		for (int i = 0; i < p.get(a).rate.size(); i++) {
			same = findSame(p.get(a).cultId, p.get(u).cultId, same);
			if (same[0] == -1)
				break;
			aArr.add(same[0]);
			uArr.add(same[1]);
			same[2]++;
			same[3]++;
		}
		//if ������ �ϳ��� ������
		if(aArr.size()==0)
			return -1;
		
		for (int i = 0; i < aArr.size(); i++) {
			aAvg += p.get(a).rate.get(aArr.get(i));
			uAvg += p.get(u).rate.get(uArr.get(i));
			//System.out.println(aAvg+", "+uAvg);
		}
		aAvg = aAvg / aArr.size();
		uAvg = uAvg / aArr.size();

		for (int i = 0; i < aArr.size(); i++) {
			float aR = p.get(a).rate.get(aArr.get(i));
			float uR = p.get(u).rate.get(uArr.get(i));
			sum1 += (aR - aAvg) * (uR - uAvg);
			sum2 += Math.pow(aR - aAvg, 2);
			sum3 += Math.pow(uR - uAvg, 2);
		}
		sim = (float) (sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3)));
		return sim;

	}

	int[] findSame(ArrayList<Integer> a, ArrayList<Integer> u, int[] stage) {
		// ����!! sorting�� �Ǿ� �ִ� �Ϲ����� arrayList�� ���� ��
		//���� �̰� �׳� ���⼭ sorting��Ű���� �ؾ߰ڴ�.
		ArrayList<Integer> atmp = new ArrayList();
		ArrayList<Integer> utmp = new ArrayList();
		atmp.addAll(a);
		utmp.addAll(u);
		int num=-1;
		int[] same = { -1, -1,-1,-1 };
		int i = stage[2];
		int j = stage[3];

		Collections.sort(atmp);
		Collections.sort(utmp);
		while (atmp.size() > i && utmp.size() > j) {
			if (atmp.get(i) < utmp.get(j)) {
				i++;
			} else if (atmp.get(i) > utmp.get(j)) {
				j++;
			} else {
				num = atmp.get(i);
				break;
			}
		}
		//num�� �����ϴ� ������ index����
		same[0] = a.indexOf(num);
		same[1] = u.indexOf(num);
		same[2] = i;
		same[3] = j;
		//System.out.println(num);
		return same;
	}
}

class EQ2 {
	float expect(int x, int a, float[] simPerson, ArrayList<Person> p) {
		// a�� ������ n������ x�� ���� ���絵
		// x�� ���� n���� �� ������ ��ü �������� ����� �ʿ�.
		// ��� : a�� x�� ������ ���� ����
		// ��, �����޴� x�� id�� ���� �������� ���������� ��. 
		float expection;
		float rA = ave(a, p);
		float rV;
		float sum1 = 0, sum2 = 0, sum3 = 0;
		int returnCount=0;
		for (int i = 0; i < simPerson.length; i++) {
			// ������ simPerson���Դ� '���絵' ���� �ο��Ǿ��� �ִ�.
			// �̵��� 'x'�� ���� �򰡸� �� ���� �־�� �Ѵ�. �׷���������, �ɷ�����.
			int index = p.get(i).cultId.indexOf(x);
			if(index != -1){
				rV = ave(i, p);
				sum1 += p.get(i).rate.get(index) - rV;
				sum2 += simPerson[i];
				sum3 += Math.abs(simPerson[i]);
				returnCount++;
			}
		}
		//�ƹ��� �ش� �������� �򰡸� ������ �ʾ��� ��� �⺻���� 2.5��
		if(returnCount ==0)
			return (float)2.5;
		expection = rA + sum1 * sum2 / sum3;
		return expection;
	}

	float ave(int perNum, ArrayList<Person> p) {
		//perNum�� �ش��ϴ� ����� �����ű� ��� ��ȭ�������� ���� ��������� ���Ѵ�. 
		int sum = 0;
		float ave = 0;
		int scoreSize = p.get(perNum).cultId.size();
		for (int i = 0; i < scoreSize; i++) {
			sum += p.get(perNum).rate.get(i);
		}
		ave = sum / scoreSize;
		return ave;
	}
	int[] findSame(ArrayList<Integer> a, ArrayList<Integer> u, int[] stage) {
		// ����!! sorting�� �Ǿ� �ִ� �Ϲ����� arrayList�� ���� ��
		int[] same = { -1, -1 };
		int i = stage[0];
		int j = stage[1];

		while (a.size() > i && u.size() > j) {
			if (a.get(i) < u.get(j)) {
				i++;
			} else if (a.get(i) > u.get(j)) {
				j++;
			} else {
				same[0] = i;
				same[1] = j;
				return same;
			}
		}
		return same;
	}
}
