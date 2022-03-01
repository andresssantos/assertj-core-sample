package io.andressantos.assertjcoresample;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionTest {

    @Test
    void shouldAssertThatAllTransactionsDatesAreSysdate() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction3 = Transaction.builder().id(3L).value(BigDecimal.TEN).date(LocalDate.now()).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2, transaction3);

        Statement statement = Statement.builder().account("123").transactions(transactionList).build();

        Condition<Transaction> sysdateCondition = new Condition<>(
                m -> m.getDate().equals(LocalDate.now()),
                "SYSDATE"
        );

        assertThat(statement.getTransactions())
                .are(sysdateCondition);
    }

    @Test
    void shouldAssertThatAllTransactionsDatesAllMatchSysdate() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction3 = Transaction.builder().id(3L).value(BigDecimal.TEN).date(LocalDate.now()).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2, transaction3);

        Statement statement = Statement.builder().account("123").transactions(transactionList).build();

        Condition<Transaction> sysdateCondition = new Condition<>(
                m -> m.getDate().equals(LocalDate.now()),
                "SYSDATE"
        );

        assertThat(statement.getTransactions())
                .flatMap(Transaction::getDate)
                .allMatch(date -> date.equals(LocalDate.now()));
    }

    @Test
    void shouldAssertThatTransactionsAreExactlyTwoTimesSysdate() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction3 = Transaction.builder().id(3L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2, transaction3);

        Statement statement = Statement.builder().account("123").transactions(transactionList).build();

        Condition<Transaction> sysdateCondition = new Condition<>(
                m -> m.getDate().equals(LocalDate.now()),
                "SYSDATE"
        );

        assertThat(statement.getTransactions())
                .areExactly(2, sysdateCondition);
    }

    @Test
    void shouldAssertThatElementIsNotWithinList() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction3 = Transaction.builder().id(3L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList = List.of(transaction2, transaction3);

        assertThat(transaction1)
                .isNotIn(transactionList);
    }

    @Test
    void shouldAssertThatListContainsAllElementsOfAnother() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction3 = Transaction.builder().id(3L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList1 = List.of(transaction1, transaction2);
        List<Transaction> transactionList2 = List.of(transaction1, transaction2, transaction3);

        assertThat(transactionList2)
                .containsAll(transactionList1);
    }
}
