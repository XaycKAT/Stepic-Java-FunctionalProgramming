import javafx.util.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class Main {
    //2.4
    public static BiFunction<Long, Long, Long> bifun = (x, y) -> {
        Long res = 1L;
        for (Long i = x; i <= y; ++i)
            res *= i;
        return res;
    };

    //2.5
    public static Function<List<String>, List<String>> fun = strings -> {
        Set<String> set = new HashSet<String>();
        for (String s : strings)
            set.add(s);
        List list = new ArrayList();
        list.addAll(set);
        Collections.sort(list);
        return list;
    };

    //2.9
    public static <T> List<T> filter(List<T> elems, Predicate<T> predicate) {
        List<T> res = new ArrayList<T>();
        for (T t : elems) {
            if (predicate.test(t))
                res.add(t);
        }
        return res;
    }

    public static List<Account> nonEmptyAccounts(List<Account> accounts) {
        return filter(accounts, account -> account.getBalance() > 0);
    }

    public static List<Account> accountsWithTooMuchMoney(List<Account> accounts) {
        return filter(accounts, account -> account.getBalance() >= 100_000_000L && !account.isLocked);
    }

    //2.10
    public static final TernaryIntPredicate allValuesAreDifferentPredicate = (x, y, z) -> {
        if (!x.equals(y) && !y.equals(z) && !x.equals(z))
            return true;
        return false;
    };

    //2.12

    /**
     * The method represents a disjunct operator for a list of predicates.
     * For an empty list it returns the always false predicate.
     */
    public static IntPredicate disjunctAll(List<IntPredicate> predicates) {
        IntPredicate res = new IntPredicate() {
            @Override
            public boolean test(int value) {
                return false;
            }
        };
        for (IntPredicate intPredicate : predicates) {
            res = res.or(intPredicate);
        }
        return res;
    }


    private static class Student {
        private String name;
        private Integer csGrade;

        public Student(String name, Integer csGrade) {
            this.name = name;
            this.csGrade = csGrade;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", csGrade=" + csGrade +
                    '}';
        }

        public String getName() {
            return name;
        }

        public Integer getCsGrade() {
            return csGrade;
        }
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(new Student("Antonio",85),
                new Student("Alisa",100),
                new Student("Elizabet",97),
                new Student("Francis",85),
                new Student("John",100),
                new Student("Vladimir",94),
                new Student("Mary",82)
        );

        students.sort(comparing(Student::getCsGrade, reverseOrder())
                        .thenComparing(Student::getName));
        for (Student student : students){
            System.out.println(student.toString());
        }
    }
}
