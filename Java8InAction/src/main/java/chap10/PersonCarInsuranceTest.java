package chap10;

import java.util.Optional;

public class PersonCarInsuranceTest {

    public static void main(String[] args){
        //使用map
        Insurance insurance=new Insurance();
        Optional<Insurance> optInsurance=Optional.ofNullable(insurance);
        Optional<String> name=optInsurance.map(Insurance::getName);

        Person person=new Person();
        Optional<Person> optPerson=Optional.of(person);
        Optional<String> name2=
                optPerson.map(Person::getCar)
                        .get()//抛出NoSuchElementException
                        .map(Car::getInsurance)
                        .get()
                        .map(Insurance::getName);

        //使用flatMap
        Person person2=new Person();
        System.out.println(new PersonCarInsuranceTest().getCarInsuranceName4(Optional.of(person2)));
    }

    public String getCarInsuranceName(Person person){
        // return person.getCar().getInsurance().getName();
        return person.getCar().get().getInsurance().get().getName();
    }
    //null-深层质疑
    public String getCarInsuranceName2(Person person){
        /*if(person!=null){
            Car car=person.getCar();
            if(car!=null){
                Insurance insurance=car.getInsurance();
                if(insurance!=null){
                    return insurance.getName();
                }
            }
        }*/
        return "Unknown";
    }
    //null-过多的退出语句
    public String getCarInsuranceName3(Person person){
        if(person==null) return "Unknown";
        // Car car=person.getCar();
        // if(car==null) return "Unknown";
        // Insurance insurance=car.getInsurance();
        // if(insurance==null) return "Unknown";
        // return insurance.getName();
        return null;
    }

    //faltMap
    public String getCarInsuranceName4(Optional<Person> person){
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    public Insurance findCheapestInsurance(Person person,Car car){
        //不同的保险公司提供的查询服务
        //对比所有数据
        Insurance cheapestCompany=new Insurance();
        return cheapestCompany;
    }

    //和null的判断差不多
    public Optional<Insurance> nullSafeFindCheapestInsurance(
            Optional<Person> person,Optional<Car> car){
        if(person.isPresent() && car.isPresent()){
            return Optional.of(findCheapestInsurance(person.get(),car.get()));
        }else {
            return Optional.empty();
        }
    }

    //完美
    public Optional<Insurance> nullSafeFindCheapestInsurance2(
            Optional<Person> person,Optional<Car> car){
        return person.flatMap(p->car.map(c->findCheapestInsurance(p,c)));
    }

    public void getInsuranceName(Insurance insurance){
        if(insurance!=null && "CambridgeInsurance".equals(insurance.getName())){
            System.out.println("ok");
        }
    }

    public void getInsuranceName2(Optional<Insurance> insurance){
        Optional<Insurance> optionalInsurance=Optional.empty();
        optionalInsurance.filter(i->"CambridgeInsurance".equals(i.getName()))
                .ifPresent(x->System.out.println("ok"));
    }

    public String getCarInstanceName(Optional<Person> person,int minAge){
        return person.filter(p->p.getAge()>=minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }























}
