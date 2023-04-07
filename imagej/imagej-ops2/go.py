from scyjava import jimport
import scyjava.config
from types import MethodType
jars=scyjava.config.find_jars("C:\\Users\\hiner\\code\\scijava\\incubator\\imagej\\imagej-ops2\\target\\dependency\\")
scyjava.config.add_classpath(*jars)
scyjava.config.add_classpath("C:\\Users\\hiner\\code\\scijava\\incubator\\imagej\\imagej-ops2\\target\\imagej-ops2-0-SNAPSHOT.jar")
env=jimport('org.scijava.ops.engine.DefaultOpEnvironment')()
print(len(env.infos()))

op_names={str(name) for info in env.infos() for name in info.names()}

#ns={op[:op.index('.')] for op in op_names if op.find('.') >= 0}

class OpNamespace:
    def __init__(self, env, ns):
        self.env = env
        self.ns = ns

class OpGateway(OpNamespace):
    def __init__(self, env):
        super().__init__(env, 'global')

def add_op(c, op_name):
    if hasattr(c, op_name):
        return

    def f(self, *args, **kwargs):
        fqop = op_name if self.ns == 'global' else self.ns + "." + op_name
        run = kwargs.get('run', True)
        # inplace
        # ops.filter.gauss(image, 5, inplace=0)
        if (inplace:=kwargs.get('inplace', None)) is not None:
            b=self.env.op(fqop).input(*args)
            return b.mutate(inplace) if run else b.inplace(inplace)

        # computer
        # ops.filter.gauss(image, 5, out=result)
        if (out:=kwargs.get('out', None)) is not None:
            b=self.env.op(fqop).input(*args).output(out)
            return b.compute() if run else b.computer()

        # function
        # gauss_op = ops.filter.gauss(image, 5)
        # result = ops.filter.gauss(image, 5)
        b = self.env.op(fqop).input(*args)
        return b.apply() if run else b.function()

    if c == OpGateway:
        setattr(c, op_name, MethodType(f, OpNamespace))
    else:
        setattr(c, op_name, MethodType(f, c))

def add_namespace(c, ns, op_name):
    if not hasattr(c, ns):
        setattr(c, ns, OpNamespace(env, ns))
    add_op(getattr(c, ns), op_name)

for op in op_names:
    dot = op.find('.')
    if dot >= 0:
        ns=op[:dot]
        op_name=op[dot+1:]
        add_namespace(OpGateway, ns, op_name)
    else:

        add_op(OpGateway, op)
